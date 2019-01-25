package jhmk.clinic.cms.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * excel导出工具类
 * @author swq
 * desc:   如何使用：
 *          //设置表头
 *         String[] headers = {"疾病名称","病例数量"};
 *         //插入表头信息
 *         ExportExcelUtil.createTitle(headers);
 *         //插入excel数据
 *         ExportExcelUtil.writeRowsToExcel(listObject,1);
 *         //设置excel宽度自适应
 *         ExportExcelUtil.autoSizeColumns(headers.length);
 *         String fileName = "疾病名称";
 *         //导出excel
 *         ExportExcelUtil.exportExcel(fileName,response);
 */
public class ExportExcelUtil {

    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private static XSSFSheet sheet = workbook.createSheet();

    /**
     * 创建表头
     * @param headers 创建表头
     *
     */
    public static void createTitle(String[] headers){
        XSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.autoSizeColumn(1);
        //设置为居中加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);

        XSSFCell cell;
        int rowNum = 0;
        for(String str : headers){
            cell = row.createCell(rowNum++);
            cell.setCellValue(str);
            cell.setCellStyle(style);
        }
    }

    /**
     *生成excel
     * @param rowIndex 从第几行生成数据（不包含表头）
     */
    public static void writeRowsToExcel(List<List<Object>> rows, int rowIndex) {
        int colIndex;

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }

                colIndex++;
            }
            rowIndex++;
        }
    }


    /**
     *
     自适应宽度(中文支持)
      */
    public static void autoSizeColumns(int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    XSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            columnWidth = columnWidth* 256;
            //设置最大宽度
            int maxColumnWidth = 6000 ;
            if(columnWidth>maxColumnWidth){
                columnWidth = maxColumnWidth;
            }
            sheet.setColumnWidth(columnNum, columnWidth );
        }
    }
    /**
     * 下载excel
     */
    public static void exportExcel(String filename, HttpServletResponse response) throws Exception{
        // 告诉浏览器用什么软件可以打开此文件
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename+".xlsx", "utf-8"));
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     *功能描述
     *@author swq
     *@date 2019-1-25  11:31
     *@param: fileName
     *@return void
     *@desc 导入到本地磁盘
     */
    public static void exportExcelToDisk(String fileName) throws Exception{
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
    }
}
