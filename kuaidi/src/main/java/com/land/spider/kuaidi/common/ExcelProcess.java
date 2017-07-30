package com.land.spider.kuaidi.common;

import com.land.spider.kuaidi.bean.KuaidiDangBean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelProcess {


    private Logger logger = LogManager.getLogger(getClass());

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void writeExcel(List<Map> dataList, int cloumnCount,String finalXlsxPath){
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);
                String name = dataMap.get("BankName").toString();
                String address = dataMap.get("Addr").toString();
                String phone = dataMap.get("Phone").toString();
                for (int k = 0; k <= columnNumCount; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(name);

                    Cell second = row.createCell(1);
                    second.setCellValue(address);

                    Cell third = row.createCell(2);
                    third.setCellValue(phone);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }


    public List<HashMap<String,String>> read(String excelPath) throws IOException {
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        File excelFile = new File(excelPath);
        if(!excelFile.exists()){
            logger.error("文件不存在"+excelPath+"]");
        }
        Workbook workBook = getWorkbok(excelFile);
        // sheet 对应一个工作页
        Sheet sheet = workBook.getSheetAt(0);
        /**
         * 删除原有数据，除了属性列
         */
        int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算
        logger.info("共：" + rowNumber+"行");
        for (int i = 1; i <= rowNumber; i++) {
            Row row = sheet.getRow(i);
            String 快递公司名称  = row.getCell(1).getStringCellValue();
            String 快递单号  = row.getCell(2).getStringCellValue();
            Cell stateCell = row.getCell(4);
            String state = "" ;
            if(stateCell == null){

            }else{
                state  = stateCell.getStringCellValue();//3 已签收
            }
            if(state.equals("3")){
                continue;
            }
            logger.info("快递公司名称:"+快递公司名称+"]快递单号:"+快递单号+"]state:"+state+"]");

            HashMap kuaidihao = new HashMap();
            kuaidihao.put("kuaidiID",快递单号);
            kuaidihao.put("kuaidiType",快递公司名称);
            list.add(kuaidihao);
        }
        return list;
    }

    public void save(HashMap<String,KuaidiDangBean> kuaididangHashMap,String excelPath) throws IOException,NullPointerException {
        OutputStream out = null;
        File excelFile = new File(excelPath);
        if(!excelFile.exists()){
            logger.error("文件不存在"+excelPath+"]");
        }
        Workbook workBook = getWorkbok(excelFile);
        // sheet 对应一个工作页
        Sheet sheet = workBook.getSheetAt(0);

        int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算
        logger.info("共：" + rowNumber+"行");
        for (int i = 1; i <= rowNumber; i++) {
            Row row = sheet.getRow(i);
            String 快递公司名称  = row.getCell(1).getStringCellValue();
            String 快递单号  = row.getCell(2).getStringCellValue();
            Cell stateCell = row.getCell(4);
            String state = "" ;
            if(stateCell == null){

            }else{
                state  = stateCell.getStringCellValue();//3 已签收
            }

            logger.info("2快递公司名称:"+快递公司名称+"]快递单号:"+快递单号+"]");

            KuaidiDangBean dang = null;
            if(stateCell == null || (!state.equals("3"))){
                dang = kuaididangHashMap.get(快递单号);
            }
            if(dang!=null){
                Cell contextCell = row.getCell(3);
                if(contextCell == null){
                    contextCell = row.createCell(3);
                }
                contextCell.setCellValue(dang.getContext());

                stateCell = row.getCell(4);
                if(stateCell == null){
                    stateCell = row.createCell(4);
                }
                stateCell.setCellValue(dang.getState());

                Cell ftimeCell = row.getCell(5);
                if(ftimeCell == null){
                    ftimeCell = row.createCell(5);
                }
                ftimeCell.setCellValue(dang.getFtime());
            }

        }

        // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
        out =  new FileOutputStream(excelPath);
        workBook.write(out);
    }
}
