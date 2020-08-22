package com.upic.utils.excel;

import com.upic.utils.util.UpicBeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.upic.utils.excel.Correspondence.getTranslationWords;

public class ExcelDocument {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ExcelDocument.class);

    private static final String DEFAULT_TYPE = "xlsx";

    /**
     * excel解析
     *
     * @param f
     * @param baseModel
     * @param c
     * @param fileName
     * @return
     */
    public static List<Object> upload(InputStream f, String[] baseModel, Class<?> c, String fileName) {
        List<Object> list = new ArrayList<Object>();
        Workbook rwb = null;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (".xls".equals(suffix)) {
                // 支持07版本以前的excel
                POIFSFileSystem p = new POIFSFileSystem(f);
                rwb = new HSSFWorkbook(p);
            } else if (".xlsx".equals(suffix)) {
                // 支持07版本以后的excel
                rwb = new XSSFWorkbook(f);
            } else {
                throw new Exception("不支持此类型");
            }
            Sheet aSheet = rwb.getSheetAt(0);
            // 获取最后一行
            int lastRow = aSheet.getLastRowNum();
            // 初始化检查
            initCheck(aSheet, lastRow);
            // 表头信息检查
            String[] firstRowFields = checkFirst(aSheet, baseModel);

            doForeach(aSheet, firstRowFields, lastRow, baseModel, c, list);
        } catch (Exception e) {
            LOGGER.error("upload" + e.getMessage());
            list.clear();
            list.add(0, e.getMessage());
        }
        return list;
    }

    public static Workbook download(String[] baseModel, Class<?> c, List<Object> data) {
        return download(baseModel, c, data, DEFAULT_TYPE);
    }

    /**
     * excel封装下载
     *
     * @param baseModel
     * @param c
     * @param data
     */
    public static Workbook download(String[] baseModel, Class<?> c, List<Object> data, String type) {
        Workbook wb = type == "xls" ? new HSSFWorkbook() : new XSSFWorkbook();
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Sheet sheet = wb.createSheet(c.getSimpleName() + sdf.format(new Date()));
            Row row = sheet.createRow((int) 0);// 表头
            Cell cell;
            // 可设置style
            for (int i = 0; i < baseModel.length; i++) {
                cell = row.createCell((short) i);
                cell.setCellValue(getTranslationWords(baseModel[i]));
            }

            // 内容
            for (int i = 0; i < data.size(); i++) {
                row = sheet.createRow((int) i + 1);
                Object object = data.get(i);
                Map<String, Method> fieldToMap = UpicBeanUtils.getMethodToMap(object.getClass(), "get");
                for (int j = 0; j < baseModel.length; j++) {
                    Method method = null;
                    Object invoke = null;
                    if ((baseModel[j].equals("projectNum") || baseModel[j].equals("studentNum"))
                            && c.getSimpleName().equals("IntegralLogInfo")) {
                        method = fieldToMap.get("integrallogid");
                        // Method#invoke 用目标对象执行
                        invoke = method.invoke(object);
                        Map<String, Method> invokeMap = UpicBeanUtils.getMethodToMap(invoke.getClass(), "get");
                        method = invokeMap.get(baseModel[j].toLowerCase());
                        invoke = method.invoke(invoke);
                    } else {
                        method = fieldToMap.get(baseModel[j].toLowerCase());
                        invoke = method.invoke(object);

                    }
                    doJugeType(invoke, row, j);
                }
            }
        } catch (Exception e) {
            LOGGER.error("download:" + e.getMessage());
            wb = null;
        }
        return wb;
    }

    private static void initCheck(Sheet aSheet, int lastRow) throws Exception {
        if (aSheet == null) {
            LOGGER.error("workbook.getSheetAt(0) is null!");
            throw new Exception("上传的文档中没有内容！");
        }
        if (lastRow > 5000) {
            throw new Exception("文档记录超过了5000条，请拆分文档");
        }
    }

    /**
     * 检查首行
     *
     * @param aSheet
     * @param baseModel
     * @return
     * @throws Exception
     */
    private static String[] checkFirst(Sheet aSheet, String[] baseModel) throws Exception {
        Row row = aSheet.getRow(0);
        if (row.getLastCellNum() != baseModel.length) {
            throw new Exception("表格头部字段数量不符合规范");
        }
        List<String> first = new ArrayList<String>();
        List<String> error = new ArrayList<String>();
        for (int i = 0; i < row.getLastCellNum(); i++) {
            for (int j = 0; j < baseModel.length; j++) {
                Cell cell = row.getCell(i);
                String cellValue = getCellValue(cell);
                if (cellValue.equals(baseModel[j])) {
                    first.add(cellValue.toLowerCase());
                    break;
                }
                if (j == baseModel.length - 1) {
                    error.add(cellValue);
                }
            }
        }
        if (first.size() != row.getLastCellNum()) {
            String msg = "表头字段名称不符合规范(";
            for (String e : error) {
                msg += e + " ";
            }
            msg += ")";
            throw new Exception(msg);
        }
        return first.toArray(new String[baseModel.length]);
    }

    /**
     * @return 得到当前列的值
     * @author LuoB.
     */
    private static String getCellValue(Cell cell) {
        String param = "";
        if (cell == null) {
            param = "0";
        } else {
            CellType type = cell.getCellTypeEnum();
            switch (type) {
                case NUMERIC:
                    Double d = cell.getNumericCellValue();
                    param = d.toString();
                    break;
                case STRING:
                    param = cell.getStringCellValue();
                    break;
                default:
                    param = "0";
                    break;
            }
        }
        return param;
    }

    /**
     * 添加字段
     *
     * @param param
     * @param c
     * @throws IllegalAccessException
     * @throws Exception
     */
    private static void addData(String firstRowField, String param, Object c) throws Exception {
        Map<String, Method> methodToMap = UpicBeanUtils.getMethodToMap(c.getClass());
        try {
            methodToMap.get(firstRowField).invoke(c, param);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 分装数据
     *
     * @param aSheet
     * @param firstRowFields
     * @param lastRow
     * @param baseModel
     * @param c
     * @param list
     * @throws Exception
     */
    private static void doForeach(Sheet aSheet, String[] firstRowFields, int lastRow, String[] baseModel, Class<?> c,
                                  List<Object> list) throws Exception {

        // 循环取出每行 然后 循环每一列
        for (int i = 1; i < lastRow; i++) {
            Object o = c.newInstance();
            Row row = aSheet.getRow(i); // 得到 第 n 行
            int lastCell = row.getLastCellNum(); // 得到 n行的总列数
            if (lastCell != baseModel.length) { // 不是 6 的就有问题
                throw new Exception("第【" + (row.getRowNum() + 1) + "】行,总列数不正确!");
            } else {
                for (int j = 0; j < lastCell; j++) {
                    Cell cells = row.getCell(j); // 得到每行 第 n列
                    if (cells == null) {
                        throw new Exception(
                                "第【" + (row.getRowNum() + 1) + "】行【" + (j + 1) + "】列," + firstRowFields[j] + "为空!");
                    } else {
                        String param = getCellValue(cells); // 解析当前列的值
                        addData(firstRowFields[j], param, o);
                    }
                }
            }
            list.add(o);
        }
    }

    /**
     * 类型转换
     *
     * @param invoke
     * @param row
     * @param j
     */
    private static void doJugeType(Object invoke, Row row, int j) {
        if (null != invoke) {
            if (invoke instanceof Date) {// 时间字段
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                row.createCell(j).setCellValue(sdf.format(invoke));
            } // 数字
            else if (invoke instanceof Integer || invoke instanceof Double || invoke instanceof Float) {
                row.createCell(j).setCellValue(Double.parseDouble(invoke + ""));
            } else {
                row.createCell(j).setCellValue(String.valueOf(invoke));
            }
        } else {
            row.createCell(j).setCellValue("");
        }
    }
}
