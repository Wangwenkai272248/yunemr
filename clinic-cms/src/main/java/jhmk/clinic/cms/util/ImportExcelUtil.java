package jhmk.clinic.cms.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author songw
 * @DATE 2019-1-24  17:43
 * @DESCRIPTION TODO
 */
public class ImportExcelUtil {
    private static Workbook wb = null;
    private static Sheet sheet = null;

    public static List<Map<String,Object>> parseExcelData(MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        try{
            if (originalFileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(file.getInputStream());
            }else{
                wb = new HSSFWorkbook(file.getInputStream());
            }
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        List<Map<String,Object>> listSheets = new ArrayList<>();
        for(int sheetSize=0;sheetSize<wb.getNumberOfSheets();sheetSize++){
            Map<String,Object> map = new HashMap<>();
            List<List<Object>> listObject = new ArrayList<>();
            sheet = wb.getSheetAt(sheetSize);
            //获得总列数
            int coloumNum=sheet.getRow(0).getPhysicalNumberOfCells();
            List headers = new ArrayList(coloumNum);

            //默认为总行数-1
            for (int i = 0; i < sheet.getLastRowNum()+1; i++){
                List<Object> list = new ArrayList<>();
                //获取索引为i的行，以0开始
                Row row = sheet.getRow(i);
                for(int j=0;j<coloumNum;j++){
                    //把第一行的表头存起来
                    if(i==0){
                        if(row !=null && row.getCell(j)!=null) {
                            headers.add(row.getCell(j).getStringCellValue());
                        }else{
                            headers.add("");
                        }
                    }else{
                        String name = "";
                        //先设置单个cell的类型，否则遇到数字类型，getStringCellValue会报异常
                        if(row !=null && row.getCell(j)!=null){
                            row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                            //获取第i行的索引为j的单元格数据
                            name= row.getCell(j).getStringCellValue();
                        }
                        list.add(name);
                    }
                }
                if(i!=0){
                    listObject.add(list);
                }
            }
            map.put("headers",headers);
            map.put("data",listObject);
            listSheets.add(map);
        }

        return listSheets;
    }
}
