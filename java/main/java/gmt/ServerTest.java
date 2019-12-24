package gmt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;

import utils.JsonUtil;

public class ServerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String strServer = "{\"2\":1,\"3\":1,\"4\":1,\"5\":1,\"6\":1,\"7\":1,\"8\":1,\"9\":1,\"10\":1,\"11\":1,\"12\":1,\"13\":1,\"14\":1,\"15\":1,\"16\":1,\"17\":1,\"18\":1,\"19\":1,\"20\":1,\"21\":1,\"22\":1,\"23\":1,\"24\":1,\"25\":1,\"26\":1,\"27\":1,\"28\":1,\"29\":1,\"30\":1,\"31\":1,\"32\":1,\"33\":1,\"34\":1,\"35\":1,\"36\":1,\"37\":1,\"38\":1,\"39\":1,\"40\":1,\"41\":1,\"42\":1,\"43\":1,\"44\":1,\"45\":1,\"46\":1,\"47\":1,\"48\":1,\"49\":1,\"50\":1,\"51\":1,\"52\":1,\"53\":1,\"54\":1,\"55\":1,\"56\":1,\"57\":1,\"58\":1,\"59\":1,\"60\":1,\"61\":1,\"62\":1,\"63\":1,\"64\":1,\"65\":1,\"66\":1,\"67\":1,\"68\":1,\"69\":1,\"70\":1,\"71\":1,\"72\":1,\"73\":1,\"74\":1,\"75\":1,\"76\":1,\"77\":1,\"78\":1,\"79\":1,\"80\":1,\"81\":1,\"82\":1,\"83\":1,\"84\":1,\"85\":1,\"86\":1,\"87\":1,\"88\":1,\"89\":1,\"90\":1,\"91\":1,\"92\":1,\"93\":1,\"94\":1,\"95\":1,\"96\":1,\"97\":1,\"98\":1,\"99\":1,\"100\":1,\"101\":1,\"102\":1,\"103\":1,\"104\":1,\"105\":1,\"60002\":60001,\"60003\":60001,\"60004\":60001,\"60005\":60001,\"60006\":60001,\"60007\":60001,\"60008\":60001,\"60009\":60001,\"60010\":60001,\"60011\":60001,\"60012\":60001,\"60013\":60001,\"60014\":60001,\"60015\":60001,\"60016\":60001,\"60017\":60001,\"60018\":60001,\"60019\":60001,\"60020\":60001,\"60021\":60001,\"60022\":60001,\"60023\":60001,\"60024\":60001,\"60025\":60001,\"60026\":60027,\"60028\":60027,\"60029\":60027,\"60030\":60027,\"60031\":60027,\"60032\":60027,\"60033\":60027,\"60034\":60027,\"60035\":60027,\"60036\":60027,\"60037\":60027,\"60038\":60027,\"60039\":60027,\"60040\":60027,\"60041\":60027,\"60042\":60027,\"60043\":60027,\"60044\":60027,\"60045\":60027,\"60047\":60046,\"60048\":60046,\"60049\":60046,\"60051\":60050,\"60053\":60052,\"50002\":50001,\"50003\":50001,\"50004\":50001,\"50005\":50001,\"50006\":50001,\"50007\":50001,\"50008\":50001,\"50009\":50001,\"50010\":50001,\"50011\":50001,\"50012\":50001,\"50013\":50001,\"50014\":50001,\"50015\":50001,\"50016\":50001,\"50017\":50001,\"50018\":50001,\"50019\":50001,\"50020\":50001,\"50021\":50001,\"50022\":50001,\"50023\":50001,\"50024\":50001,\"50025\":50001,\"50026\":50001,\"50027\":50001,\"50028\":50001,\"50029\":50001,\"50030\":50001,\"50031\":50001,\"50032\":50001,\"50033\":50001,\"50034\":50001,\"50035\":50001,\"50036\":50001,\"50037\":50001,\"50038\":50001,\"50039\":50001,\"50040\":50001,\"50041\":50001,\"50042\":50001,\"50043\":50001,\"50044\":50001,\"50045\":50001,\"50046\":50001,\"50047\":50001,\"50048\":50001,\"50049\":50001,\"50050\":50001,\"50051\":50001,\"50052\":50001,\"50053\":50001,\"50054\":50001,\"50055\":50001,\"50056\":50001,\"50057\":50001,\"50058\":50001,\"50059\":50001,\"50060\":50001,\"50061\":50001,\"50062\":50001,\"50063\":50001,\"50064\":50001,\"50065\":50001,\"50066\":50001,\"50067\":50001,\"50068\":50001,\"50069\":50001,\"50070\":50001,\"50071\":50001,\"50072\":50001,\"50073\":50001,\"50074\":50001,\"50075\":50001,\"50076\":50001,\"50077\":50121,\"50078\":50121,\"50079\":50121,\"50080\":50121,\"50081\":50121,\"50082\":50121,\"50083\":50121,\"50084\":50121,\"50085\":50121,\"50086\":50121,\"50087\":50121,\"50088\":50121,\"50089\":50121,\"50090\":50121,\"50091\":50121,\"50092\":50121,\"50093\":50121,\"50094\":50121,\"50095\":50121,\"50096\":50121,\"50097\":50121,\"50098\":50121,\"50099\":50121,\"50100\":50121,\"50101\":50121,\"50102\":50121,\"50103\":50121,\"50104\":50121,\"50105\":50121,\"50106\":50121,\"50107\":50121,\"50108\":50121,\"50109\":50121,\"50110\":50121,\"50111\":50121,\"50112\":50121,\"50113\":50121,\"50114\":50121,\"50115\":50121,\"50116\":50121,\"50117\":50121,\"50118\":50121,\"50119\":50121,\"50120\":50121,\"50122\":50121,\"50123\":50121,\"50124\":50121,\"50125\":50121,\"50126\":50121,\"50127\":50121,\"50128\":50121,\"50129\":50121,\"50130\":50121,\"50131\":50121,\"50132\":50121,\"50133\":50121,\"50134\":50121,\"50135\":50121,\"50136\":50121,\"50137\":50121,\"50138\":50121,\"50139\":50121,\"50140\":50121,\"50141\":50121,\"50142\":50121,\"50143\":50121,\"50144\":50121,\"50145\":50121,\"50146\":50121,\"50147\":50121,\"50148\":50121,\"50149\":50121,\"50150\":50121,\"50151\":50121,\"40001\":40141,\"40002\":40141,\"40003\":40141,\"40004\":40141,\"40005\":40141,\"40006\":40141,\"40007\":40141,\"40008\":40141,\"40009\":40141,\"40011\":40010,\"40012\":40141,\"40013\":40010,\"40014\":40010,\"40015\":40010,\"40016\":40010,\"40017\":40010,\"40018\":40010,\"40019\":40141,\"40020\":40141,\"40021\":40141,\"40022\":40141,\"40023\":40010,\"40024\":40141,\"40025\":40010,\"40026\":40010,\"40027\":40010,\"40028\":40010,\"40029\":40010,\"40030\":40010,\"40031\":40010,\"40032\":40010,\"40033\":40010,\"40034\":40010,\"40035\":40010,\"40036\":40010,\"40037\":40010,\"40038\":40010,\"40039\":40010,\"40040\":40010,\"40041\":40010,\"40042\":40010,\"40043\":40010,\"40044\":40010,\"40045\":40010,\"40046\":40010,\"40047\":40010,\"40048\":40010,\"40049\":40010,\"40050\":40010,\"40051\":40010,\"40052\":40010,\"40053\":40010,\"40054\":40010,\"40055\":40010,\"40056\":40010,\"40057\":40010,\"40058\":40010,\"40059\":40010,\"40060\":40010,\"40061\":40010,\"40062\":40010,\"40063\":40010,\"40064\":40010,\"40065\":40010,\"40066\":40010,\"40067\":40010,\"40068\":40010,\"40069\":40010,\"40070\":40010,\"40071\":40010,\"40072\":40010,\"40073\":40010,\"40074\":40010,\"40075\":40010,\"40076\":40010,\"40077\":40010,\"40078\":40010,\"40079\":40010,\"40080\":40010,\"40081\":40010,\"40082\":40010,\"40083\":40010,\"40084\":40010,\"40085\":40010,\"40086\":40010,\"40087\":40010,\"40088\":40010,\"40089\":40010,\"40090\":40010,\"40091\":40010,\"40092\":40010,\"40093\":40010,\"40094\":40010,\"40095\":40010,\"40096\":40010,\"40097\":40010,\"40098\":40010,\"40099\":40010,\"40100\":40010,\"40101\":40010,\"40102\":40010,\"40103\":40010,\"40104\":40010,\"40105\":40010,\"40106\":40010,\"40107\":40010,\"40108\":40010,\"40109\":40010,\"40110\":40010,\"40111\":40010,\"40112\":40010,\"40113\":40010,\"40114\":40010,\"40115\":40010,\"40116\":40010,\"40117\":40010,\"40118\":40010,\"40119\":40010,\"40120\":40010,\"40121\":40010,\"40122\":40010,\"40123\":40010,\"40124\":40010,\"40125\":40010,\"40126\":40010,\"40127\":40010,\"40128\":40010,\"40129\":40010,\"40130\":40010,\"40131\":40010,\"40132\":40010,\"40133\":40010,\"40134\":40010,\"40135\":40010,\"40136\":40010,\"40137\":40010,\"40138\":40010,\"40139\":40010,\"40140\":40010,\"40142\":40010,\"40143\":40010,\"40144\":40010,\"40145\":40141,\"40146\":40141,\"40147\":40141,\"40148\":40141,\"40149\":40141,\"40150\":40141,\"40151\":40141,\"40152\":40141,\"40153\":40141,\"40154\":40141,\"40155\":40141,\"40156\":40141,\"40157\":40141,\"40158\":40141,\"40159\":40141,\"40160\":40141,\"40161\":40141,\"40162\":40141,\"40163\":40141,\"40164\":40141,\"40165\":40141,\"40166\":40141,\"40167\":40141,\"40168\":40141,\"40169\":40141,\"40170\":40141,\"40171\":40141,\"40172\":40141,\"40173\":40141,\"40174\":40141,\"40175\":40141,\"40176\":40141,\"40177\":40141,\"40178\":40141,\"40179\":40141,\"40180\":40141,\"40181\":40141,\"40182\":40141,\"40183\":40141,\"40184\":40141,\"40185\":40141,\"40186\":40141,\"40187\":40141,\"40188\":40141,\"40189\":40141,\"40190\":40141,\"40191\":40141,\"40192\":40141,\"40193\":40141,\"40194\":40141,\"40195\":40141,\"40196\":40141,\"40197\":40141,\"40198\":40141,\"40199\":40141,\"40200\":40141,\"40201\":40141,\"40202\":40141,\"40203\":40141,\"40204\":40141,\"40205\":40141,\"40206\":40141,\"40207\":40141,\"40208\":40141,\"40209\":40141,\"40210\":40141,\"40211\":40141,\"40312\":40311,\"40313\":40311,\"40314\":40311,\"40315\":40311,\"40316\":40311,\"40317\":40311,\"40318\":40311,\"40319\":40311,\"40320\":40311,\"40321\":40311,\"40322\":40311,\"40323\":40311,\"40324\":40311,\"40325\":40311,\"40326\":40311,\"40327\":40311,\"40328\":40311,\"40329\":40311,\"40330\":40311,\"40331\":40311,\"40332\":40311,\"40333\":40311,\"40334\":40311,\"40335\":40311,\"40336\":40311,\"40337\":40311,\"40338\":40311,\"40339\":40311,\"40340\":40311,\"40341\":40311,\"40342\":40311,\"40343\":40311,\"40344\":40311,\"40345\":40311,\"40346\":40311,\"40347\":40311,\"40348\":40311,\"40349\":40311,\"40350\":40311,\"40351\":40311,\"40352\":40311,\"40353\":40311,\"40354\":40311,\"40355\":40311,\"40356\":40311,\"40357\":40311,\"40358\":40311,\"40359\":40311,\"40360\":40311,\"40361\":40311,\"40363\":40362,\"40364\":40362,\"40365\":40362,\"40366\":40362,\"40367\":40362,\"40368\":40362,\"40369\":40362,\"40370\":40362,\"40371\":40362,\"40372\":40362,\"40373\":40362,\"40374\":40362,\"40375\":40362,\"40376\":40362,\"40377\":40362,\"40378\":40362,\"40379\":40362,\"40380\":40362,\"40381\":40362,\"40382\":40362,\"40383\":40362,\"40384\":40362,\"40385\":40362,\"40386\":40362,\"40387\":40362,\"40388\":40362,\"40389\":40362,\"40390\":40362,\"40391\":40362,\"40392\":40362,\"40393\":40362,\"40394\":40362,\"40395\":40362,\"40396\":40362,\"40397\":40362,\"40398\":40362,\"40399\":40362,\"40400\":40362,\"40401\":40362,\"40402\":40362,\"40403\":40362,\"40404\":40362,\"40405\":40362,\"40406\":40426,\"40407\":40426,\"40408\":40426,\"40409\":40426,\"40410\":40426,\"40411\":40426,\"40412\":40426,\"40413\":40426,\"40414\":40426,\"40415\":40426,\"40416\":40426,\"40417\":40426,\"40418\":40426,\"40419\":40426,\"40420\":40426,\"40421\":40426,\"40422\":40426,\"40423\":40426,\"40424\":40426,\"40425\":40426,\"40427\":40426,\"40428\":40426,\"40429\":40426,\"40430\":40426,\"40431\":40426,\"40432\":40426,\"40433\":40426,\"40434\":40426,\"40435\":40426,\"40436\":40426,\"40437\":40426,\"40438\":40426,\"40439\":40426,\"40440\":40426,\"40441\":40426,\"40442\":40426,\"40443\":40426,\"40444\":40426,\"40445\":40426,\"40446\":40426,\"40447\":40426,\"40448\":40426,\"40449\":40426,\"40450\":40426,\"40451\":40426,\"40452\":40426,\"40453\":40426,\"40454\":40426,\"40455\":40426,\"40456\":40426,\"40457\":40426,\"40458\":40426,\"40459\":40426,\"40460\":40426,\"40461\":40426,\"40462\":40426,\"40463\":40426,\"40464\":40426,\"40465\":40426,\"40466\":40481,\"40467\":40481,\"40468\":40481,\"40469\":40481,\"40470\":40481,\"40471\":40481,\"40472\":40481,\"40473\":40481,\"40474\":40481,\"40475\":40481,\"40476\":40481,\"40477\":40481,\"40478\":40481,\"40479\":40481,\"40480\":40481,\"20001\":20003,\"20002\":20003,\"40482\":40481,\"40483\":40481,\"20004\":20003,\"40484\":40481,\"20005\":20003,\"40485\":40481,\"20006\":20003,\"40486\":40481,\"20007\":20003,\"40487\":40481,\"20008\":20003,\"40488\":40481,\"20009\":20003,\"40489\":40481,\"20010\":20003,\"40490\":40481,\"20011\":20003,\"40491\":40481,\"20012\":20003,\"40492\":40481,\"20013\":20003,\"40493\":40481,\"20014\":20003,\"40494\":40481,\"20015\":20003,\"40495\":40481,\"20016\":20003,\"40496\":40481,\"20017\":20003,\"40497\":40481,\"20018\":20003,\"40498\":40481,\"20019\":20003,\"40499\":40481,\"20020\":20003,\"40500\":40481,\"20021\":20003,\"40501\":40481,\"20022\":20003,\"40502\":40481,\"20023\":20003,\"40503\":40481,\"20024\":20003,\"20025\":20003,\"40505\":40504,\"20026\":20003,\"40506\":40504,\"20027\":20003,\"40507\":40504,\"20028\":20003,\"40508\":40504,\"20029\":20003,\"40509\":40504,\"20030\":20003,\"40510\":40504,\"20031\":20003,\"40511\":40504,\"20032\":20003,\"40512\":40504,\"20033\":20003,\"40513\":40504,\"20034\":20003,\"40514\":40504,\"20035\":20003,\"40515\":40504,\"20036\":20003,\"40516\":40504,\"20037\":20003,\"40517\":40504,\"20038\":20003,\"40518\":40504,\"20039\":20003,\"40519\":40504,\"20040\":20003,\"20041\":20003,\"40521\":40520,\"20042\":20003,\"40522\":40520,\"20043\":20003,\"40523\":40520,\"20044\":20003,\"40524\":40520,\"20045\":20003,\"40525\":40504,\"20046\":20003,\"40526\":40504,\"20047\":20003,\"40527\":40504,\"20048\":20003,\"40528\":40504,\"20049\":20003,\"40529\":40504,\"20050\":20003,\"40530\":40504,\"20051\":20003,\"40531\":40504,\"20052\":20003,\"40532\":40504,\"20053\":20003,\"40533\":40520,\"20054\":20003,\"40534\":40520,\"20055\":20003,\"40535\":40520,\"20056\":20003,\"40536\":40520,\"20057\":20003,\"40537\":40504,\"20058\":20003,\"40538\":40504,\"20059\":20003,\"40539\":40504,\"20060\":20003,\"40540\":40504,\"20061\":20003,\"40541\":40520,\"20062\":20003,\"40542\":40520,\"20063\":20003,\"40543\":40520,\"20064\":20003,\"40544\":40520,\"20065\":20003,\"40545\":40504,\"20066\":20003,\"40546\":40504,\"20067\":20003,\"40547\":40520,\"20068\":20003,\"40548\":40520,\"20069\":20003,\"40549\":40520,\"20070\":20003,\"40550\":40520,\"20071\":20003,\"40551\":40520,\"20072\":20003,\"40552\":40520,\"20073\":20003,\"40553\":40520,\"20074\":20003,\"40554\":40520,\"20075\":20003,\"40555\":40520,\"20076\":20003,\"40556\":40520,\"20077\":20003,\"40557\":40558,\"20078\":20003,\"20079\":20003,\"40559\":40558,\"20080\":20003,\"40560\":40558,\"20081\":20003,\"40561\":40558,\"20082\":20003,\"40562\":40558,\"20083\":20003,\"40563\":40558,\"20084\":20003,\"40564\":40558,\"20085\":20003,\"40565\":40558,\"20086\":20003,\"40566\":40558,\"20087\":20003,\"40567\":40558,\"20088\":20003,\"40568\":40558,\"20089\":20003,\"40569\":40558,\"20090\":20003,\"40570\":40558,\"20091\":20003,\"40571\":40558,\"20092\":20003,\"40572\":40558,\"20093\":20003,\"40573\":40558,\"20094\":20003,\"40574\":40558,\"20095\":20003,\"40575\":40558,\"20096\":20003,\"40576\":40558,\"20097\":20003,\"40577\":40558,\"20098\":20003,\"20099\":20003,\"40579\":40578,\"20100\":20003,\"40580\":40578,\"20101\":20003,\"40581\":40578,\"20102\":20003,\"40582\":40578,\"20103\":20003,\"40583\":40578,\"20104\":20003,\"40584\":40578,\"20105\":20003,\"40585\":40578,\"20106\":20003,\"40586\":40578,\"20107\":20003,\"20108\":20003,\"40588\":40587,\"20109\":20003,\"40589\":40587,\"20110\":20003,\"40590\":40587,\"20111\":20003,\"20112\":20003,\"40592\":40591,\"20113\":20003,\"40593\":40591,\"20114\":20003,\"40594\":40591,\"20115\":20003,\"20116\":20003,\"40596\":40595,\"20117\":20003,\"20118\":20003,\"40598\":40597,\"20119\":20003,\"20120\":20003,\"40600\":40599,\"20121\":20003,\"20122\":20003,\"40602\":40601,\"20123\":20003,\"20124\":20003,\"40604\":40603,\"20125\":20003,\"20126\":20003,\"20127\":20003,\"20128\":20003,\"20129\":20003,\"20130\":20003,\"20131\":20003,\"20132\":20003,\"20133\":20003,\"20134\":20003,\"20135\":20003,\"20136\":20003,\"20137\":20003,\"20138\":20003,\"20139\":20003,\"20140\":20003,\"20141\":20003,\"20142\":20003,\"20143\":20003,\"20144\":20003,\"20145\":20003,\"20146\":20003,\"20147\":20003,\"20148\":20003,\"20149\":20003,\"20150\":20003,\"20151\":20003,\"20152\":20003,\"20153\":20003,\"20154\":20003,\"20155\":20003,\"20156\":20003,\"20157\":20003,\"20158\":20003,\"20159\":20003,\"20160\":20003,\"20161\":20003,\"20162\":20003,\"20163\":20003,\"20164\":20003,\"20165\":20003,\"20166\":20003,\"20167\":20003,\"20168\":20003,\"20169\":20003,\"20170\":20003,\"20171\":20003,\"20172\":20003,\"20173\":20003,\"20174\":20003,\"20175\":20003,\"20176\":20003,\"20177\":20003,\"20178\":20003,\"20179\":20003,\"20180\":20003,\"20181\":20003,\"20182\":20003,\"20183\":20003,\"20184\":20003,\"20185\":20003,\"20186\":20003,\"20187\":20003,\"20189\":20188,\"20190\":20188,\"20191\":20188,\"20192\":20188,\"20193\":20003,\"20194\":20003,\"20195\":20188,\"20196\":20188,\"20197\":20188,\"20198\":20188,\"20199\":20188,\"20200\":20188,\"20201\":20188,\"20202\":20188,\"20203\":20188,\"20204\":20188,\"20205\":20188,\"20206\":20188,\"20207\":20188,\"20208\":20188,\"20209\":20188,\"20210\":20188,\"20211\":20188,\"20212\":20188,\"20213\":20188,\"20214\":20188,\"20215\":20188,\"20216\":20188,\"20217\":20188,\"20218\":20188,\"20219\":20188,\"20220\":20188,\"20221\":20188,\"20222\":20188,\"20223\":20188,\"20224\":20188,\"20225\":20188,\"20226\":20188,\"20227\":20188,\"20228\":20188,\"20229\":20188,\"20230\":20188,\"20231\":20188,\"20232\":20188,\"20233\":20188,\"20234\":20188,\"20235\":20188,\"20236\":20188,\"20237\":20188,\"20238\":20188,\"20239\":20188,\"20240\":20188,\"20241\":20188,\"20242\":20188,\"20243\":20188,\"20244\":20188,\"20245\":20188,\"20246\":20188,\"20247\":20188,\"20248\":20188,\"20249\":20188,\"20250\":20188,\"20252\":20251,\"20253\":20251,\"20254\":20251,\"20255\":20251,\"20256\":20251,\"20257\":20251,\"20258\":20251,\"20260\":20259,\"20262\":20261}";
		try {
			Map<String, Integer> map = JsonUtil.TransToObject(strServer, HashMap.class);
			SortedSet<Integer> set = new TreeSet<Integer>(map.values());
			System.out.println(JsonUtil.TransToJson(set));
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}