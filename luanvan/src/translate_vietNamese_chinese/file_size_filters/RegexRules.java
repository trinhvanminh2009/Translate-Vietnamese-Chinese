package translate_vietNamese_chinese.file_size_filters;

import java.util.ArrayList;
import java.util.List;


public class RegexRules {
	public static final String ALPHABET = "[A-Za-záàạảãâấầậẩẫăắằặẳẵÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴéèẹẻẽêếềệểễÉÈẸẺẼÊẾỀỆỂỄóòọỏõôốồộổỗơớờợởỡÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠúùụủũưứừựửữÚÙỤỦŨƯỨỪỰỬỮíìịỉĩÍÌỊỈĨđĐýỳỵỷỹÝỲỴỶỸ_0-9 ]+";

	public static final String EMAIL = "([\\w\\d_\\.-]+)@(([\\d\\w-]+)\\.)*([\\d\\w-]+)";

	public static final String FULL_DATE = "(ngày )?(?<day>0?[1-9]|[12][0-9]|3[01])(( tháng )|\\/|-|\\.)(?<month>1[0-2]|(0?[1-9]))(( năm )|\\/|-|\\.)(?<years>\\d{4})";

	public static final String MONTH = "(tháng )?(?<month>1[0-2]|(0?[1-9]))((( năm )|\\/|-)(?<years>\\d{4}))";

	public static final String DATE = "(ngày )?(?<day>0?[1-9]|[12][0-9]|3[01])(( tháng )|\\/|-)(?<month>1[0-2]|(0?[1-9]))";

	public static final String TIME = "(((\\d\\d|0?\\d)(:|h))?(\\d\\d|0?\\d)(â€™|'|p|ph|:)(\\d\\d|0?\\d)(s)?)";

	public static final String MONEY = "\\p{Sc}\\d+([\\.,]\\d+)*|\\d+([\\.,]\\d+)*\\p{Sc}";

	public static final String PHONE_NUMBER = "(\\(?\\+\\d{1,2}\\)?[\\s\\.-]?)?\\d{2,}[\\s\\.-]?\\d{3,}[\\s\\.-]?\\d{3,}";

	public static final String URL = "(((https?|ftp|http):\\/\\/|www\\.)[^\\s/$.?#].[^\\s]*)|(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";

	public static final String PERCENT = "([-+]?\\d+([\\.,]\\d+)*)(%)";

	public static final String NUMBER = "[-+]?\\d+([\\.,]\\d+)*";

	public static final String SPECIAL_CHAR = "\\~|\\@|\\#|\\^|\\&|\\*|\\+|\\-|\\Ă¢â‚¬â€œ|<|>|\\|";

	public static final String DECREE_SYMBOL = "((" + ALPHABET + "+)(-))?(" + ALPHABET + "+)(((-)(" + ALPHABET
			+ "+))*)(([1-9][0-9]+|0?[1-9])?)";

	public static final String DECREE = "(([1-9][0-9]+|0?[1-9])(\\/)((([12]\\d{3})(\\/))?)(" + DECREE_SYMBOL + "))";

	public static final String PUNCTUATION = "[^\\w\\s\\dA-Za-záàạảãâấầậẩẫăắằặẳẵÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴéèẹẻẽêếềệểễÉÈẸẺẼÊẾỀỆỂỄóòọỏõôốồộổỗơớờợởỡÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠúùụủũưứừựửữÚÙỤỦŨƯỨỪỰỬỮíìịỉĩÍÌỊỈĨđĐýỳỵỷỹÝỲỴỶỸ]";

	public static final String REFERENT_DECREE = "(((tại khoản|tại Khoản )([1-9][0-9]+|0?[1-9])((( và Khoản )([1-9][0-9]+|0?[1-9]))*)"
			+ "( Điều )([1-9][0-9]+|0?[1-9]))" + ALPHABET + DECREE + "((" + ALPHABET + FULL_DATE + ")?)" + ")";

	public static final String SPACE_CHARACTER = "(\\s)+";

	public static final String SPECIAL_WORD = ALPHABET + "([\\-'‘’&]" + ALPHABET + ")+";

	public static final String SPECIAL_NAME ="([a-zA-Z0-9][a-zA-Z0-9]*[\\.][a-zA-Z0-9][a-zA-Z0-9]*)";

	public static final String MEASURE = NUMBER
			+ "((Ge|K|k|M|m|G|g|T|t|P|p|E|e|Z|z|Y|y|B|b)?(b|B)|(cc|ml|m3|dm3|cm3)|(k|K|Da|da|H|h|m|M)?(m|M|G|g){1}|(s))";

	public static final String ABBREVIATION="(BT|Co|Corp|Dr|Ltd|Miss|MISS|MR.|Mr|MRS|Mrs|MS|Ms|P|Q.|Tp|TP|TT|TBT)(\\.)";
	private static List<String> regexes = null;

	private static List<String> regexIndex = null;

	public static List<String> GetRegexList() {
		if (regexes == null) {
			regexes = new ArrayList<String>();
			regexIndex = new ArrayList<String>();

			regexes.add(FULL_DATE);
			regexIndex.add("FULL_DATE");

			regexes.add(REFERENT_DECREE);
			regexIndex.add("REFERENT_DECREE");

			regexes.add(DECREE);
			regexIndex.add("DECREE");

			regexes.add(EMAIL);
			regexIndex.add("EMAIL");

			regexes.add(URL);
			regexIndex.add("URL");

			regexes.add(MONEY);
			regexIndex.add("MONEY");

			regexes.add(PHONE_NUMBER);
			regexIndex.add("PHONE_NUMBER");

			//regexes.add(PERCENT);
			//regexIndex.add("PERCENT");

			regexes.add(SPECIAL_WORD);
			regexIndex.add("SPECIAL_WORD");

			regexes.add(MONTH);
			regexIndex.add("MONTH");

			regexes.add(DATE);
			regexIndex.add("DATE");

			regexes.add(TIME);
			regexIndex.add("TIME");

			regexes.add(SPECIAL_NAME);
			regexIndex.add("SPECIAL_NAME");
			
			//regexes.add(ABBREVIATION);
			//regexIndex.add("ABBREVIATION");

			regexes.add(SPECIAL_CHAR);
			regexIndex.add("SPECIAL_CHAR");

			regexes.add(NUMBER);
			regexIndex.add("NUMBER");

			regexes.add(PUNCTUATION);
			regexIndex.add("PUNCTUATION");

		}

		return regexes;
	}

	public static int GetRegexIndex(String regex) {
		return regexIndex.indexOf(GetNameRules(regex));
	}

	public static String GetNameRules(String rules) {
		switch (rules) {
		case EMAIL:
			return "EMAIL";
		case FULL_DATE:
			return "FULL_DATE";
		case MONTH:
			return "MONTH";
		case DATE:
			return "DATE";
		case TIME:
			return "TIME";
		case MONEY:
			return "MONEY";
		case PHONE_NUMBER:
			return "PHONE_NUMBER";
		case URL:
			return "URL";
		case NUMBER:
			return "NUMBER";
		case SPECIAL_CHAR:
			return "SPECIAL_CHAR";
		case DECREE:
			return "DECREE";
		case REFERENT_DECREE:
			return "REFERENT_DECREE";
		case SPECIAL_WORD:
			return "SPECIAL_WORD";
		case SPECIAL_NAME:
			return "SPECIAL_NAME";
		case PERCENT:
			return "PERCENT";
		case MEASURE:
			return "MEASURE";
		/*case ABBREVIATION:
			return "ABBREVIATION";*/
		default:
			return rules;
		}
	}
}