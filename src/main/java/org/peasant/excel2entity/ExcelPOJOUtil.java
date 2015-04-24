package org.peasant.excel2entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.peasant.util.AbstractConverter;
import org.peasant.util.ConvertUtil;
import org.peasant.util.Converter;
import org.peasant.util.Converters;
import org.peasant.util.ReflectUtil;

/**
 * Excel转化为POJO的功能基本完成，目前转换办法都是静态的，考虑性能问题，未来再改为非静态，为每个POJO类提供一个此类的实例。 TODO
 * POJOs导出为Excel的功能待完善。
 *
 * @author 谢金光
 */
public class ExcelPOJOUtil {

    /**
     * @param <T>
     * @MethodName : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
     * @param list 数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系 如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式
     * 如：list中存放的都是student，student中又有college属性，而我们需要学院名称，则可以这样写
     * fieldMap.put("college.collegeName","学院名称")
     * @param sheetName 工作表的名称
     * @param sheetSize 每个工作表中记录的最大个数
     * @param out 导出流
     * @throws ExcelException
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            int sheetSize,
            OutputStream out
    ) throws ExcelException {

        if (null == list || list.isEmpty()) {
            throw new ExcelException("数据源中没有任何数据");
        }

        if (sheetSize > 65535 || sheetSize < 1) {
            sheetSize = 65535;
        }

        //创建工作簿并发送到OutputStream指定的地方 
        WritableWorkbook wwb;
        try {
            wwb = Workbook.createWorkbook(out);

            //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条 
            //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程 
            //1.计算一共有多少个工作表 
            double sheetNum = Math.ceil(list.size() / new Integer(sheetSize).doubleValue());

            //2.创建相应的工作表，并向其中填充数据 
            for (int i = 0; i < sheetNum; i++) {
                //如果只有一个工作表的情况 
                if (1 == sheetNum) {
                    WritableSheet sheet = wwb.createSheet(sheetName, i);
                    fillSheet(sheet, list, fieldMap, 0, list.size() - 1);

                    //有多个工作表的情况 
                } else {
                    WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);

                    //获取开始索引和结束索引 
                    int firstIndex = i * sheetSize;
                    int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list.size() - 1 : (i + 1) * sheetSize - 1;
                    //填充工作表 
                    fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
                }
            }

            wwb.write();
            wwb.close();

        } catch (Exception e) {
            e.printStackTrace();
            //如果是ExcelException，则直接抛出 
            if (e instanceof ExcelException) {
                throw (ExcelException) e;

                //否则将其它异常包装成ExcelException再抛出 
            } else {
                throw new ExcelException("导出Excel失败");
            }
        }

    }

    /**
     * @MethodName : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，工作表大小为2003支持的最大值）
     * @param list 数据源
     * @param fieldConf 类的英文属性和Excel中的中文列名的对应关系
     * @param out 导出流
     * @throws ExcelException
     */
    public static <T> void listToExcel(List<T> list, LinkedHashMap<String, String> fieldConf, String sheetName, OutputStream out) throws ExcelException {

        listToExcel(list, fieldConf, sheetName, 65535, out);

    }

    /**
     * @MethodName : listToExcel
     * @Description : 导出Excel（导出到浏览器，可以自定义工作表的大小）
     * @param list 数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param sheetSize 每个工作表中记录的最大个数
     * @param response 使用response可以导出到浏览器
     * @throws ExcelException
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            int sheetSize,
            HttpServletResponse response
    ) throws ExcelException {

        //设置默认文件名为当前时间：年月日时分秒 
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();

        //设置response头信息 
        response.reset();
        response.setContentType("application/vnd.ms-excel");        //改成输出excel文件 
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

        //创建工作簿并发送到浏览器 
        try {

            OutputStream out = response.getOutputStream();
            listToExcel(list, fieldMap, sheetName, sheetSize, out);

        } catch (Exception e) {
            e.printStackTrace();

            //如果是ExcelException，则直接抛出 
            if (e instanceof ExcelException) {
                throw (ExcelException) e;

                //否则将其它异常包装成ExcelException再抛出 
            } else {
                throw new ExcelException("导出Excel失败");
            }
        }
    }

    /**
     * @MethodName : listToExcel
     * @Description : 导出Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     * @param list 数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param response 使用response可以导出到浏览器
     * @throws ExcelException
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            HttpServletResponse response
    ) throws ExcelException {

        listToExcel(list, fieldMap, sheetName, 65535, response);
    }

    public static int getRealRowCount(Sheet sheet) {
        //获取工作表的有效行数
        int realRows = 0;
        for (int i = 0; i < sheet.getRows(); i++) {

            int nullCols = 0;
            for (int j = 0; j < sheet.getColumns(); j++) {
                Cell currentCell = sheet.getCell(j, i);
                if (currentCell == null || "".equals(currentCell.getContents())) {
                    nullCols++;
                }
            }

            if (nullCols == sheet.getColumns()) {
                break;
            } else {
                realRows++;
            }
        }
        return realRows;
    }

    /**
     * 推荐使用此方法
     *
     * @param <T>
     * @param is
     * @param sheetName
     * @param entityClazz
     * @param fieldConf
     * @throws java.io.IOException
     * @throws org.peasant.util.ExcelPOJOUtil.ExcelException
     * @return the java.util.Collection
     */
    public static <T> Collection<T> worksheetToPOJOs(InputStream is, String sheetName, Class<T> entityClazz, java.util.Properties fieldConf, Converters converters) throws ExcelException, IOException {
        try {
            Workbook wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet(sheetName);
            if (null == sheet) {
                sheet = wb.getSheet(0);
            }
            return worksheetToPOJOs(sheet, entityClazz, fieldConf, converters);
        } catch (BiffException ex) {
            Logger.getLogger(ExcelPOJOUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcelException(sheetName, ex);
        }
    }

    /**
     *
     * @param <T>
     * @param sheet
     * @param entityClazz
     * @param fieldConf
     * @param converters
     * @throws org.peasant.util.ExcelPOJOUtil.ExcelException
     * @return the java.util.Collection
     */
    protected static <T> Collection<T> worksheetToPOJOs(Sheet sheet, Class<T> entityClazz, java.util.Properties fieldConf, Converters converters) throws ExcelException {

        //获取Excel中的列名 
        Cell[] heads = sheet.getRow(0);
        //将列名和列号放入Map中,这样通过列名就可以拿到列号 
        Map<String, Integer> columnNameIndexMap = new HashMap<>(heads.length);
        //  Map<Integer,String> columnIndexNameMap = new HashMap<>(heads.length);
        for (int i = 0; i < heads.length; i++) {

            if (!(heads[i].getContents() == null || "".equals(heads[i].getContents()))) {
                columnNameIndexMap.put(heads[i].getContents().trim(), i);
                //columnIndexNameMap.put(i, heads[i].getContents().trim());
            }
        }
        int rowCount = sheet.getRows();
        Collection<T> results = new ArrayList<>(rowCount - 1);

        int realRows = getRealRowCount(sheet);

        //如果Excel中没有数据则提示错误 
        if (realRows <= 1) {
            throw new ExcelException("Excel文件中没有任何数据");
        }
        //定义要返回的list 

        //判断需要的字段在Excel中是否都存在 
        Configuration conf = new Configuration(fieldConf);
        for (String cnName : conf.getRequiredFieldMap().keySet()) {
            if (!columnNameIndexMap.containsKey(cnName)) {
                //如果有列名不存在，则抛出异常，提示错误 
                throw new ExcelException("Excel中缺少必要的字段:\"" + cnName + "\"，或字段名称有误");
            }
        }

        String[] uniqueColumns = null;//TODO
        //判断是否有重复行 //TODO 配置文件中可以配置唯一列，或组合唯一
        if (uniqueColumns != null && uniqueColumns.length > 0) {
            //1.获取uniqueFields指定的列 
            Cell[][] uniqueCells = new Cell[uniqueColumns.length][];
            for (int i = 0; i < uniqueColumns.length; i++) {
                int col = columnNameIndexMap.get(uniqueColumns[i]);
                uniqueCells[i] = sheet.getColumn(col);
            }

            //2.从指定列中寻找重复行,此算法存在问题，正常情况是当全部列值一样的行才能称为重复行（TODO）
            for (int i = 1; i < realRows; i++) {
                int nullCols = 0;
                for (int j = 0; j < uniqueColumns.length; j++) {
                    String currentContent = uniqueCells[j][i].getContents();
                    Cell sameCell = sheet.findCell(currentContent,
                            uniqueCells[j][i].getColumn(),
                            uniqueCells[j][i].getRow() + 1,
                            uniqueCells[j][i].getColumn(),
                            uniqueCells[j][realRows - 1].getRow(),
                            true);
                    if (sameCell != null) {
                        nullCols++;
                    }
                }

                if (nullCols == uniqueColumns.length) {
                    throw new ExcelException("Excel中有重复行，请检查");
                }
            }
        }

        for (int i = 1; i < rowCount; i++) {
            try {
                //对每一行记录按照给定的列与实体的属性对应关系，给实体的属性进行赋值
                T entity = entityClazz.newInstance();
                Cell[] row = sheet.getRow(i);
                Map<String, FieldDescriptor> colFieldMap = conf.getColumnFieldMap();
                for (Entry<String, FieldDescriptor> e : colFieldMap.entrySet()) {//只对配置文件中指定的列进行赋值,并且按出现的先后顺序进行字段赋值(实现中暂不能保证先后顺序，因读取配置是使用的是hashMap)
                    if(!columnNameIndexMap.containsKey(e.getKey()))//如果属性未出现在Excel的Sheet的字段中，则不进行赋值
                        continue;
                    String property = e.getValue().getFieldName();//Could be concatenated property name;
                    Object value = null;
                    String sValue = row[columnNameIndexMap.get(e.getKey())].getContents();
                    Class propertyType = ReflectUtil.getConcatenatedPropertyType(property, entityClazz);
                    if(propertyType==null){
                        throw new ExcelException("在POJO类："+entityClazz+"中找不到指定的属性:"+property);
                    }
                    if (converters != null) {//使用提供的Converters进行转换
                        Converter ctr = null;
                        try {
                            ctr = converters.getConverter(propertyType);
                        } catch (Exception ex) {
                            Logger.getLogger(ExcelPOJOUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (ctr != null) {
                            value = ctr.convert(sValue);
                        } else {
                            value = ConvertUtil.convert(sValue, propertyType);
                        }
                    } else {//若未提供对应类型的Converter,则尝试使用ConvertUtil对值进行转换
                        value = ConvertUtil.convert(sValue, propertyType);

                    }
                    ReflectUtil.setConcatenatedPropertyByName(property, value, entity);

                }
                results.add(entity);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(ExcelPOJOUtil.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcelException("在将Excel行记录转换为POJO时出现异常！", ex);

            }
        }
        return results;
    }

    /**
     * 查找特定工作表中，指定起始行、结束行间的空白行的row index,zerobase.
     *
     * @param sheet
     * @param startRow the beginning row index, inclusive.zero based;
     * @param endRow the ending row index, exclusive.zero based
     * @return 空白行的行索引数组
     */
    public static Integer[] findBlankRows(Sheet sheet, int startRow, int endRow) {
        boolean isBlank = true;
        int cols = sheet.getColumns();

        List<Integer> blankRows = new ArrayList();
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < cols; j++) {
                if ((sheet.getCell(j, i).getContents() != null) && (!"".equals(sheet.getCell(j, i).getContents().trim()))) {
                    isBlank = false;
                    break;
                }

            }
            if (isBlank) {
                blankRows.add(i);
            }

        }
        return (Integer[]) blankRows.toArray();

    }

    /**
     *
     * @param sheet
     * @param startRow
     * @param endRow
     * @param uniqueColumns
     * @return
     */
    public Integer[] findDuplicatedRows(Sheet sheet, int startRow, int endRow, int[] uniqueColumns) {
        return null;//TODO
    }

    /**
     * 查找特定工作表中，指定起始行、结束行间的,指定的列中存在的空白单元格.
     *
     * @param sheet
     * @param startRow the beginning row index, inclusive.zero based;
     * @param endRow the ending row index, exclusive.zero based
     * @param columnIndexs
     * @return 空白单元格的索引，如果未找到空白单元格的话，则返回一个empty Map
     */
    public static Map<Integer, Collection<Integer>> findBlankOrNullCell(Sheet sheet, int startRow, int endRow, int[] columnIndexs) {
        Map<Integer, Collection<Integer>> m = new HashMap<>();
        for (int i = startRow; i < endRow; i++) {
            for (int j : columnIndexs) {
                if (null == sheet.getCell(j, i).getContents() || "".equals(sheet.getCell(j, i).getContents().trim())) {
                    if (null != m.get(i)) {
                        m.get(i).add(j);
                    } else {
                        Collection a = new ArrayList<>(columnIndexs.length);
                        a.add(j);
                        m.put(i, a);
                    }
                }

            }

        }
        return m;
    }

    /*<-------------------------辅助的私有方法----------------------------------------------->*/
    /**
     * @MethodName : getFieldValueByName
     * @Description : 根据字段名获取字段值
     * @param fieldName 字段名
     * @param o 对象
     * @return 字段值
     */
    private static Object getFieldValueByName(String fieldName, Object o) throws Exception {

        Object value = null;
        Field field = getFieldByName(fieldName, o.getClass());

        if (field != null) {
            field.setAccessible(true);
            value = field.get(o);
        } else {
            throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 " + fieldName);
        }

        return value;
    }

    /**
     * @MethodName : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?> clazz) {
        //拿到本类的所有公共字段 
        Field[] fields = clazz.getFields();

        //如果本类中存在该字段，则返回 
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        return null;
    }

    /**
     * @MethodName : getFieldValueByNameSequence
     * @Description : 根据带路径或不带路径的属性名获取属性值
     * 即接受简单属性名，如userName等，又接受带路径的属性名，如student.department.name等
     *
     * @param fieldNameSequence 带路径的属性名或简单属性名
     * @param o 对象
     * @return 属性值
     * @throws Exception
     */
    private static Object getFieldValueByNameSequence(String fieldNameSequence, Object o) throws Exception {

        Object value = null;

        //将fieldNameSequence进行拆分 
        String[] attributes = fieldNameSequence.split("\\.");
        if (attributes.length == 1) {
            value = getFieldValueByName(fieldNameSequence, o);
        } else {
            //根据属性名获取属性对象 
            Object fieldObj = getFieldValueByName(attributes[0], o);
            String subFieldNameSequence = fieldNameSequence.substring(fieldNameSequence.indexOf(".") + 1);
            value = getFieldValueByNameSequence(subFieldNameSequence, fieldObj);
        }
        return value;

    }

    /**
     * @MethodName : setColumnAutoSize
     * @Description : 设置工作表自动列宽和首行加粗
     * @param ws
     */
    private static void setColumnAutoSize(WritableSheet ws, int extraWith) {
        //获取本列的最宽单元格的宽度 
        for (int i = 0; i < ws.getColumns(); i++) {
            int colWith = 0;
            for (int j = 0; j < ws.getRows(); j++) {
                String content = ws.getCell(i, j).getContents().toString();
                int cellWith = content.length();
                if (colWith < cellWith) {
                    colWith = cellWith;
                }
            }
            //设置单元格的宽度为最宽宽度+额外宽度 
            ws.setColumnView(i, colWith + extraWith);
        }

    }

    /**
     * @MethodName : fillSheet
     * @Description : 向工作表中填充数据
     * @param sheet 工作表
     * @param list 数据源
     * @param fieldMap 中英文字段对应关系的Map
     * @param firstIndex 开始索引
     * @param lastIndex 结束索引
     */
    private static <T> void fillSheet(
            WritableSheet sheet,
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            int firstIndex,
            int lastIndex
    ) throws Exception {

        //定义存放英文字段名和中文字段名的数组 
        String[] enFields = new String[fieldMap.size()];
        String[] cnFields = new String[fieldMap.size()];

        //填充数组 
        int count = 0;
        for (Entry<String, String> entry : fieldMap.entrySet()) {
            enFields[count] = entry.getKey();
            cnFields[count] = entry.getValue();
            count++;
        }
        //填充表头 
        for (int i = 0; i < cnFields.length; i++) {
            Label label = new Label(i, 0, cnFields[i]);
            sheet.addCell(label);
        }

        //填充内容 
        int rowNo = 1;
        for (int index = firstIndex; index <= lastIndex; index++) {
            //获取单个对象 
            T item = list.get(index);
            for (int i = 0; i < enFields.length; i++) {
                Object objValue = getFieldValueByNameSequence(enFields[i], item);
                String fieldValue = objValue == null ? "" : objValue.toString();
                Label label = new Label(i, rowNo, fieldValue);
                sheet.addCell(label);
            }

            rowNo++;
        }

        //设置自动列宽 
        setColumnAutoSize(sheet, 5);

    }

    /**
     *
     */
    public static class ExcelException extends Exception {

        public ExcelException() {
            // TODO Auto-generated constructor stub 
        }

        public ExcelException(String message) {
            super(message);
            // TODO Auto-generated constructor stub 
        }

        public ExcelException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub 
        }

        public ExcelException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub 
        }

    }

    public static class FieldDescriptor {

        private final String column;
        private final String field;
        private boolean required = false;

        public FieldDescriptor(String column, String conf) {
            this.column = column;
            String[] cs = conf.split(",");
            this.field = cs[0];
            if (cs.length > 1 && "required".equalsIgnoreCase(cs[1])) {
                required = true;
            }
        }

        public String getColumnName() {
            return column;

        }

        ;

        public String getFieldName() {
            return field;
        }

        ;

        public boolean isRequired() {
            return required;
        }
    ;

    }

    public static class Configuration {

        private final Properties config;

        private final Map<String, FieldDescriptor> required = new HashMap();
        private final Map<String, FieldDescriptor> columnfield = new HashMap();
        ;
        private final List<FieldDescriptor> fields = new LinkedList<>();

        ;

        public Configuration(Properties config) {

            this.config = config;
            for (Entry e : config.entrySet()) {
                String column = (String) e.getKey();
                String conf = (String) e.getValue();
                FieldDescriptor fd = new FieldDescriptor(column, conf);
                fields.add(fd);
                columnfield.put(column, fd);
                if (fd.required) {
                    required.put(column, fd);
                }

            }

        }

        public Map<String, FieldDescriptor> getRequiredFieldMap() {
            return required;
        }

        public Map<String, FieldDescriptor> getColumnFieldMap() {
            return columnfield;
        }

        public List<FieldDescriptor> getAllFileds() {
            return fields;
        }

    }

}
