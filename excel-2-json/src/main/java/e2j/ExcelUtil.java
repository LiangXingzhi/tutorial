package e2j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	public static List<Item> readExcel(File excel) throws Exception {
		List<Item> itemList = new ArrayList<Item>(1024);
		Workbook wb = WorkbookFactory.create(excel);
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			Item item = new Item();
			for (Cell cell : row) {
				String result = null;
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					result = String.valueOf(cell.getNumericCellValue());
				} else {
					result = cell.getStringCellValue();
				}
				if ("item_name".equals(result) || "item_value".equals(result)) {
					continue;
				}
				if(cell.getColumnIndex() == 0) {
					item.setItem_name(result);
				} else if(cell.getColumnIndex() == 1) {
					item.setItem_value(result);
				}
			}
			if(item.getItem_name() != null) {
				itemList.add(item);
			}
		}
		return itemList;
	}
}