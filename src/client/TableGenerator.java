package client;

import utils.Constants;

public class TableGenerator {

	public static String[][] generate_info_table(int[] candidate_info) {
		String[][] info_table_data = new String[candidate_info.length/2][];
		for (int i=0, j=0; i<candidate_info.length; i+=2, j++) {
			int candidate_number = candidate_info[i];
			int candidate_viewable_number = candidate_number + 1; // Note - this increments the cand number to make it work
			int party_int = candidate_info[i+1];
			String party_name = (party_int == Constants.PARTY_1) ? Constants.PARTY_1_NAME : Constants.Party_2_NAME;
			
			String[] blank_row = Constants.CLIENT_INFO_ROW.clone();
			blank_row[0] = Integer.toString(candidate_viewable_number);
			blank_row[1] = party_name;
			info_table_data[j] = blank_row;
		}
		return info_table_data;
	}

	public static String[][] generate_buy_table(int[] candidate_info, int party) {
		String[][] buy_table_data = new String[candidate_info.length/2][];
		for (int i=0; i<candidate_info.length; i+=2) {
			int candidate_number = candidate_info[i];
			int candidate_viewable_number = candidate_number + 1; // Note - this increments the cand number to make it work
			int party_int = candidate_info[i+1];
			
			String[] blank_row = Constants.CLIENT_BUY_ROW.clone();
			blank_row[0] = Integer.toString(candidate_viewable_number);
			if (party_int == party) {
				blank_row[1] = Integer.toString(Constants.SAME_PARTY_PRICE);
			} else {
				blank_row[1] = Integer.toString(Constants.OTHER_PARTY_PRICE);
			}
			buy_table_data[i/2] = blank_row;
		}
		return buy_table_data;

	}

	public static String[][] generate_vote_table(int[] candidate_info) {
		String[][] vote_table_data = new String[candidate_info.length/2][];
		for (int i=0, j=0; i<candidate_info.length; i+=2, j++) {
			int candidate_number = candidate_info[i];
			int candidate_viewable_number = candidate_number + 1; // Note - this increments the cand number to make it work
			
			String[] blank_row = Constants.CLIENT_VOTE_ROW.clone();
			blank_row[0] = Integer.toString(candidate_viewable_number);
			vote_table_data[j] = blank_row;
		}
		return vote_table_data;

	}

}
