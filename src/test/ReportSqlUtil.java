package test;

import java.util.ArrayList;
import java.util.List;

public class ReportSqlUtil {
	
	public static void main(String[] args) {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" (select  a.pk_org, ");
//		sql.append(" 	       a.pk_group, ");
//		sql.append(" a.pk_glorgbook, ");
//		sql.append(" 	       a.pk_assetsprop, ");
//		sql.append(" 	       a.pk_stocksort, ");
//		sql.append(" a.transtypecode as pk_billtype, ");
//		sql.append(" a.pk_capaccount, ");
//		sql.append(" a.pk_client, ");
//		sql.append(" a.pk_operatesite, ");
//		sql.append(" a.pk_partnaccount, ");
//		sql.append(" a.pk_selfsgroup, ");
//		sql.append(" a.pk_securities, ");
//		sql.append(" a.trade_date, ");
//		sql.append(" null as sellnum, ");
//		sql.append(" null as sellsum, ");
//		sql.append(" null as sellcost, ");
//		sql.append(" a.bargain_num as buynum, ");
//		sql.append(" a.bargain_sum as buysum ");
//		sql.append(" from sim_fundsplit a ");
//		sql.append(" inner join sec_securities b ");
//		sql.append(" on a.pk_securities = b.pk_securities ");
//		sql.append(" where nvl(a.dr, 0) = 0 ");
//		sql.append(" and a.transtypecode = 'HV3E-0xx-01'  @@condition@@ ) ");
//		sql.append(" union  ");
//		sql.append(" (select d.pk_org, ");
//		sql.append(" d.pk_group, ");
//		sql.append(" d.pk_glorgbook, ");
//		sql.append(" d.pk_assetsprop, ");
//		sql.append(" d.pk_stocksort, ");
//		sql.append(" d.transtypecode as pk_billtype, ");
//		sql.append(" d.pk_capaccount, ");
//		sql.append(" d.pk_client, ");
//		sql.append(" d.pk_operatesite, ");
//		sql.append(" d.pk_partnaccount, ");
//		sql.append(" d.pk_selfsgroup, ");
//		sql.append(" c.pk_securities, ");
//		sql.append(" d.trade_date, ");
//		sql.append(" c.bargain_num as sellnum, ");
//		sql.append(" c.bargain_sum as sellsum, ");
//		sql.append(" c.bargain_sum as sellcost, ");
//		sql.append(" null as buynum, ");
//		sql.append(" null as buysum ");
//		sql.append(" from sim_fundsplit_b c ");
//		sql.append(" inner join sec_securities b ");
//		sql.append(" on c.pk_securities = b.pk_securities ");
//		sql.append(" inner join sim_fundsplit d ");
//		sql.append(" on d.pk_fundsplit = c.pk_fundsplit ");
//		sql.append(" where nvl(d.dr, 0) = 0 ");
//		sql.append(" and nvl(c.dr, 0) = 0 and d.transtypecode = 'HV3E-0xx-01' @@condition@@ ) ");   
//		String condition = "baicai";
//		
//		System.out.println(getQuerySql(condition, sql.toString()));
//		
//		System.out.println(getQuerySql(condition, "niday"));
		
		List<String> list = new ArrayList<String>();
		list.addAll(null);
	}

	public static String getQuerySql(String condition, String querySql) {

		String result = null;
//		if (querySql.matches(".+@@condition@@.+") && condition == null) {
//			result = querySql.replaceAll("@@condition@@", " ");
//		} else if (querySql.matches(".+@@condition@@.+")) {
//			result = querySql.replaceAll("@@condition@@", condition);
//		} else {
//			result = new StringBuffer(querySql).append(condition).toString();
//		}
		int index = querySql.indexOf("@@condition@@");
		if(index != -1 && condition == null){
			result = querySql.replaceAll("@@condition@@", " ");
		}else if(index != -1){
			result = querySql.replaceAll("@@condition@@", condition);
		}else{
			result = new StringBuffer(querySql).append(condition).toString();
		}

		return result;
	}
}
