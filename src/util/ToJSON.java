package util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

public class ToJSON {

	public JSONArray toJSONArray(ResultSet rs) throws Exception{
		JSONArray json = new JSONArray();
		String temp = null;
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int numColumns = rsmd.getColumnCount();
			
			while(rs.next()){
				JSONObject obj = new JSONObject();
				for(int i = 1; i < numColumns; i++){
					String columnName = rsmd.getColumnName(i);
					
					if(rsmd.getColumnType(i) == java.sql.Types.ARRAY){
						obj.put(columnName, rs.getArray(columnName));
						System.out.println("ToJson: ARRAY");
					}else if(rsmd.getColumnType(i) == java.sql.Types.BIGINT){
						obj.put(columnName, rs.getInt(columnName));
						System.out.println("ToJson: BIGINT");
					}else if(rsmd.getColumnType(i) == java.sql.Types.BOOLEAN){
						obj.put(columnName, rs.getArray(columnName));
						System.out.println("ToJson: ARRAY");
					}else if(rsmd.getColumnType(i) == java.sql.Types.BLOB){
						obj.put(columnName, rs.getBlob(columnName));
						System.out.println("ToJson: BLOB");
					}else if(rsmd.getColumnType(i) == java.sql.Types.DOUBLE){
						obj.put(columnName, rs.getDouble(columnName));
						System.out.println("ToJson: DOUBLE");
					}else if(rsmd.getColumnType(i) == java.sql.Types.FLOAT){
						obj.put(columnName, rs.getFloat(columnName));
						System.out.println("ToJson: FLOAT");
					}else if(rsmd.getColumnType(i) == java.sql.Types.INTEGER){
						obj.put(columnName, rs.getInt(columnName));
						System.out.println("ToJson: INTEGER");
					}else if(rsmd.getColumnType(i) == java.sql.Types.NVARCHAR){
						obj.put(columnName, rs.getNString(columnName));
						System.out.println("ToJson: NVARCHAR");
					}else if(rsmd.getColumnType(i) == java.sql.Types.VARCHAR){
						temp = rs.getString(columnName);
						temp = ESAPI.encoder().canonicalize(temp);
						temp = ESAPI.encoder().encodeForHTML(temp);
						obj.put(columnName, temp);
//						obj.put(columnName, rs.getString(columnName));
//						System.out.println("ToJson: VARCHAR");
					}else if(rsmd.getColumnType(i) == java.sql.Types.TINYINT){
						obj.put(columnName, rs.getInt(columnName));
						System.out.println("ToJson: TINYINT");
					}else if(rsmd.getColumnType(i) == java.sql.Types.SMALLINT){
						obj.put(columnName, rs.getInt(columnName));
						System.out.println("ToJson: SMALLINT");
					}else if(rsmd.getColumnType(i) == java.sql.Types.DATE){
						obj.put(columnName, rs.getDate(columnName));
						System.out.println("ToJson: DATE");
					}else if(rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP){
						obj.put(columnName, rs.getTimestamp(columnName));
						System.out.println("ToJson: TIMESTAMP");
					}else if(rsmd.getColumnType(i) == java.sql.Types.NUMERIC){
						obj.put(columnName, rs.getBigDecimal(columnName));
						System.out.println("ToJson: NUMERIC");
					}else{
						obj.put(columnName,  rs.getObject(columnName));
						System.out.println("ToJson: Object " + columnName);
					}
				}
				json.put(obj);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return json;
	}
}
