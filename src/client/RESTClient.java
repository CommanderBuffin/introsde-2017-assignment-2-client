package client;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import model.Activity;
import model.ActivityType;
import model.Person;

public class RESTClient {
	Client client = ClientBuilder.newClient();
	final String baseUri = "http://assignment2-denisgallo.herokuapp.com/sdelab/";

	public int first_person_id=-1;
	public int last_person_id=-1;
	public int new_person_id=-1;
	public int new_person_id_json=-1;
	public List<ActivityType> activity_types = new ArrayList<ActivityType>();
	public int activity_id=-1;
	public ActivityType activity_type;
	public int person_found_id=-1;
	public int activities_count=0;
	public int new_activity_id=-1;
	
	private String xmlToString(Object o) {
		String r = "";
		JAXBContext jaxbContext;
		Marshaller marshaller = null;
		try {
			jaxbContext = JAXBContext.newInstance(o.getClass());
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter sw = new StringWriter();
			marshaller.marshal(o, sw);
			r+=sw.toString();
		}
		catch(JAXBException ex) {ex.printStackTrace();}
		return r;
	}
	
	private String jsonToString(Object o) {
		String r = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
		try {
			 return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	public void callInit() {
		String uri = baseUri+"init";
		client.target(uri).request().get();
	}
	
	public String step1(){
		return callRequest1();
	}
	public String step2(){
		return callRequest2(first_person_id);
	}
	public String step3(){
		return callRequest3();
	}
	public String step4(){
		return callRequest4();
	}
	public String step5(){
		String r = "";
		r+=callRequest5();
		r+=callRequest2(new_person_id);
		return r;
	}
	public String step6(){
		return callRequest6();
	}
	public String step7(){
		return callRequest7();
	}
	public String step8(){
		return callRequest8();
	}
	public String step9(){
		String r = "";
		r+=callRequest7();
		int n = activities_count;
		r+=callRequest9();
		r+=callRequest7();
		int m = activities_count;
		String s ="";
		if(m-n==2)
			s ="Step 3.9 \r\n=>Result: OK\r\n";
		else
			s="Step 3.9 \r\n=>Result: ERROR\r\n";
		r+=s+"\r\n";
		System.out.println(s);
		return r;
	}
	public String step10(){
		String r = "";
		int n = activity_types.size();
		r+=callRequest10();
		r+=callRequest6();
		int m = activity_types.size();
		String s ="";
		if(m>n)
			s ="Step 3.10 \r\n=>Result: OK\r\n";
		else
			s ="Step 3.10 \r\n=>Result: ERROR\r\n";
		r+=s+"\r\n";
		return r;
	}
	public String step11(String before, String after){
		return callRequest11(before, after);
	}
	
	public String callRequest1() {
		String r = "";
		String uri = baseUri+"person";
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).get();
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get();

		List<Person> p_xml = xml.readEntity(new GenericType<List<Person>>() {});
		List<Person> p_json = json.readEntity(new GenericType<List<Person>>() {});
		
		String s_xml="";
		String s_json="";
		
		if(!p_xml.isEmpty()) {
			first_person_id = p_xml.get(0).getIdPerson();
			last_person_id = p_xml.get(p_xml.size()-1).getIdPerson();
		}
			
		for(Person p : p_xml)
		{
			s_xml += xmlToString(p)+"\r\n";
		}
		for(Person p : p_json)
		{
			s_json += jsonToString(p)+"\r\n";
		}
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #1: GET "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		String h_json = "Request #1: GET "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: ";
		
		r+=h_xml+"\r\n";
		if(p_xml.size()>4)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(p_json.size()>4)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}
	
	public String callRequest2(int person_id) {
		String r = "";
		String uri = baseUri+"person/"+person_id;
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).get();
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get();

		Person p_xml=null;
		Person p_json=null;
		try {
			p_xml = xml.readEntity(Person.class);
			p_json = json.readEntity(Person.class);
		}catch(Exception ex) {}
		
		String s_xml="";
		String s_json="";

		if(p_xml!=null&&p_json!=null) {
			s_xml += xmlToString(p_xml)+"\r\n";
			s_json += jsonToString(p_json)+"\r\n";
		}
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #2: GET "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		String h_json = "Request #2: GET "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: ";
		
		r+=h_xml+"\r\n";
		if(xml.getStatusInfo().getStatusCode()==200||xml.getStatusInfo().getStatusCode()==202)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(json.getStatusInfo().getStatusCode()==200||json.getStatusInfo().getStatusCode()==202)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}
	
	public String callRequest3() {
		String r = "";
		String uri = baseUri+"person/"+first_person_id;
		String name = "Gianmarcanfrancarletto";
		
		Person p = new Person();
		p.setFirstname(name);
		
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).put(Entity.xml(p));
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(p));

		Person p_xml = xml.readEntity(Person.class);
		Person p_json = json.readEntity(Person.class);
		
		String s_xml="";
		String s_json="";

		s_xml += xmlToString(p_xml)+"\r\n";
		s_json += jsonToString(p_json)+"\r\n";
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #3: PUT "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: "+MediaType.APPLICATION_XML;
		String h_json = "Request #3: PUT "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: "+MediaType.APPLICATION_JSON;
		
		r+=h_xml+"\r\n";
		if(p_xml.getFirstname().equals(name))
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(p_json.getFirstname().equals(name))
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}
	
	public String callRequest4() {
		String r = "";
		String uri = baseUri+"person";
		int id = Integer.MAX_VALUE;
		
		Person p = new Person();
		p.setFirstname("Mariangiongiangela");
		p.setLastname("Canterniosti");
		p.setBirthdate("1990-05-21");
		p.setIdPerson(id);
		Activity a = new Activity();
		a.setIdActivity(24);
		a.setName("Troll Denis");
		a.setDescription("Troll Denis for his server implementation");
		a.setPlace("Uni");
		a.setStartdate("2017-11-22-12:10");
		a.setPerson(p);
		ActivityType at = new ActivityType();
		at.setAtName("Studying");
		a.setType(at);
		List<Activity> aa = new ArrayList<Activity>();
		aa.add(a);
		p.setActivities(aa);
		
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(p));
		
		p.setIdPerson(id-1);
		a.setIdActivity(23);
		List<Activity> aa2 = new ArrayList<Activity>();
		aa2.add(a);
		p.setActivities(aa2);
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(p));

		Person p_xml = xml.readEntity(Person.class);
		Person p_json = json.readEntity(Person.class);
		
		new_person_id = p_xml.getIdPerson();
		new_person_id_json = p_json.getIdPerson();
		
		String s_xml="";
		String s_json="";

		s_xml += xmlToString(p_xml)+"\r\n";
		s_json += jsonToString(p_json)+"\r\n";
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #4: POST "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: "+MediaType.APPLICATION_XML;
		String h_json = "Request #4: POST "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: "+MediaType.APPLICATION_JSON;
		
		r+=h_xml+"\r\n";
		if(xml.getStatusInfo().getStatusCode()==201||xml.getStatusInfo().getStatusCode()==200||xml.getStatusInfo().getStatusCode()==202)
			if(new_person_id!=-1)
				resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(new_person_id_json!=-1)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}

	public String callRequest5() {
		String r = "";
		String uri = baseUri+"person/"+new_person_id;
		
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).delete();
		String h_xml = "Request #5: DELETE "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		
		uri = baseUri+"person/"+new_person_id_json;
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_XML).delete();
		String h_json = "Request #5: DELETE "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		
		
		r+=h_xml+"\r\n";
		r+="=> Result: \r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+="\r\n";
		
		r+=h_json+"\r\n";
		r+="=> Result: \r\n";
		r+="=> HTTP Status: "+json.getStatusInfo().getStatusCode()+"\r\n";
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}

	public String callRequest6() {
		String r = "";
		String uri = baseUri+"activity_types";
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).get();
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get();

		List<ActivityType> at_xml = xml.readEntity(new GenericType<List<ActivityType>>() {});
		List<ActivityType> at_json = json.readEntity(new GenericType<List<ActivityType>>() {});
		
		activity_types = at_xml;
		
		String s_xml="";
		String s_json="";

		if(at_xml!=null&&at_json!=null) {
			for(ActivityType at : at_xml)
			{
				s_xml += xmlToString(at)+"\r\n";
			}
			s_json += jsonToString(at_json)+"\r\n";
		}
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #6: GET "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		String h_json = "Request #6: GET "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: ";
		
		r+=h_xml+"\r\n";
		if(at_xml.size()>2)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(at_json.size()>2)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
		
	}

	public String callRequest7() {
		String r = "";
		String uri = baseUri+"person/";
		Activity xmlFoundActivity= null;
		Activity jsonFoundActivity= null;
		Response xml=null;
		Response json=null;
		int p_id=-1;
		String at_name="";
		for(ActivityType at : activity_types)
		{
			if(xmlFoundActivity==null&&jsonFoundActivity==null) {
				xml = client.target(uri+first_person_id+"/"+at.getAtName()).request().accept(MediaType.APPLICATION_XML).get();
				json = client.target(uri+first_person_id+"/"+at.getAtName()).request().accept(MediaType.APPLICATION_JSON).get();
			
				if(xml.getStatusInfo().getStatusCode()==200) {
					List<Activity> aa = xml.readEntity(new GenericType<List<Activity>>() {});
					activities_count = aa.size();
					xmlFoundActivity = aa.get(0);
					p_id=first_person_id;
					person_found_id=p_id;
					at_name=at.getAtName();
				}
				if(json.getStatusInfo().getStatusCode()==200) {
					List<Activity> aa = json.readEntity(new GenericType<List<Activity>>() {});
					jsonFoundActivity = aa.get(0);
				}
			}
		}
		if(xmlFoundActivity==null) {
			for(ActivityType at : activity_types)
			{
				if(xmlFoundActivity==null&&jsonFoundActivity==null) {
					xml = client.target(uri+last_person_id+"/"+at.getAtName()).request().accept(MediaType.APPLICATION_XML).get();
					json = client.target(uri+last_person_id+"/"+at.getAtName()).request().accept(MediaType.APPLICATION_JSON).get();
				
					if(xml.getStatusInfo().getStatusCode()==200) {
						List<Activity> aa = xml.readEntity(new GenericType<List<Activity>>() {});
						xmlFoundActivity = aa.get(0);
						p_id=last_person_id;
						person_found_id=p_id;
						at_name=at.getAtName();
					}
					if(json.getStatusInfo().getStatusCode()==200) {
						List<Activity> aa = json.readEntity(new GenericType<List<Activity>>() {});
						jsonFoundActivity = aa.get(0);
					}
				}
			}
		}
		
		String s_xml="";
		String s_json="";

		if(xmlFoundActivity!=null) {
			s_xml += xmlToString(xmlFoundActivity)+"\r\n";
			activity_id = xmlFoundActivity.getIdActivity();
			activity_type = xmlFoundActivity.getType();
		}
		if(jsonFoundActivity!=null)
			s_json += jsonToString(jsonFoundActivity)+"\r\n";
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #7: GET "+uri+p_id+"/"+at_name+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		String h_json = "Request #7: GET "+uri+p_id+"/"+at_name+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: ";
		
		r+=h_xml+"\r\n";
		if(xmlFoundActivity!=null)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(jsonFoundActivity!=null)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}

	public String callRequest8() {
		String r = "";
		String uri = baseUri+"person/"+person_found_id+"/"+activity_type.getAtName()+"/"+activity_id;
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).get();
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get();

		Activity a_xml=null;
		Activity a_json=null;
		try {
			a_xml = xml.readEntity(Activity.class);
			a_json = json.readEntity(Activity.class);
		}catch(Exception ex) {}
		
		String s_xml="";
		String s_json="";

		if(a_xml!=null&&a_json!=null) {
			s_xml += xmlToString(a_xml)+"\r\n";
			s_json += jsonToString(a_json)+"\r\n";
		}
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #8: GET "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		String h_json = "Request #8: GET "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: ";
		
		r+=h_xml+"\r\n";
		if(xml.getStatusInfo().getStatusCode()==200)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(json.getStatusInfo().getStatusCode()==200)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}

	public String callRequest9() {
		String r = "";
		String uri = baseUri+"person/"+first_person_id+"/"+activity_types.get(0).getAtName();
		
		Activity a = new Activity();
		//a.setIdActivity(123);
		a.setName("Swimming");
		a.setDescription("Swimming in the river");
		a.setPlace("Adige river");
		a.setStartdate("2017-12-28-08:50");
		a.setType(activity_types.get(0));
		
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(a));
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(a));

		Activity a_xml = xml.readEntity(Activity.class);
		Activity a_json = json.readEntity(Activity.class);
		
		String s_xml="";
		String s_json="";
		
		if(a_xml!=null && a_json!=null) {
			s_xml += xmlToString(a_xml)+"\r\n";
			s_json += jsonToString(a_json)+"\r\n";
			new_activity_id=a_xml.getIdActivity();
		}
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #9: PUT "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: "+MediaType.APPLICATION_XML;
		String h_json = "Request #9: PUT "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: "+MediaType.APPLICATION_JSON;
		
		r+=h_xml+"\r\n";
		if(a_xml!=null)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(a_json!=null)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}

	public String callRequest10() {
		String r = "";
		String uri = baseUri+"person/"+first_person_id+"/"+activity_types.get(0).getAtName()+"/"+new_activity_id;
		
		Activity a = new Activity();
		ActivityType at = new ActivityType();
		at.setAtName("Media");
		a.setType(at);
		
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).put(Entity.xml(a));
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(a));

		Activity a_xml = xml.readEntity(Activity.class);
		Activity a_json = json.readEntity(Activity.class);
		
		String s_xml="";
		String s_json="";

		s_xml += xmlToString(a_xml)+"\r\n";
		s_json += jsonToString(a_json)+"\r\n";
		
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #10: PUT "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: "+MediaType.APPLICATION_XML;
		String h_json = "Request #10: PUT "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: "+MediaType.APPLICATION_JSON;
		
		r+=h_xml+"\r\n";
		if(xml.getStatusInfo().getStatusCode()/100==2)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(json.getStatusInfo().getStatusCode()/100==2)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}
	
	public String callRequest11(String before, String after) {
		String r = "";
		String uri = baseUri+"person/"+first_person_id+"/Media?before="+before+"&after="+after;
		Response xml = client.target(uri).request().accept(MediaType.APPLICATION_XML).get();
		Response json = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get();

		List<Activity> a_xml = new ArrayList<Activity>();
		List<Activity> a_json = new ArrayList<Activity>();
		try {
			a_xml = xml.readEntity(new GenericType<List<Activity>>() {});
			a_json = json.readEntity(new GenericType<List<Activity>>() {});
		}catch(Exception ex) {}
		
		String s_xml="";
		String s_json="";
			
		for(Activity a : a_xml)
		{
			s_xml += xmlToString(a)+"\r\n";
		}
		for(Activity a : a_json)
		{
			s_json += jsonToString(a)+"\r\n";
		}
		String resultXml = "ERROR";
		String resultJson = "ERROR";
		
		String h_xml = "Request #11: GET "+uri+" Accept: "+MediaType.APPLICATION_XML+" Content-type: ";
		String h_json = "Request #11: GET "+uri+" Accept: "+MediaType.APPLICATION_JSON+" Content-type: ";
		
		r+=h_xml+"\r\n";
		if(xml.getStatusInfo().getStatusCode()==200 && a_xml.size()>0)
			resultXml = "OK";
		r+="=> Result: "+ resultXml+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_xml;
		r+="\r\n";
		
		r+=h_json+"\r\n";
		if(json.getStatusInfo().getStatusCode()==200 && a_json.size()>0)
			resultJson = "OK";
		r+="=> Result: "+ resultJson+"\r\n";
		r+="=> HTTP Status: "+xml.getStatusInfo().getStatusCode()+"\r\n";
		r+=s_json;
		r+="\r\n";
		
		System.out.println(r);
		return r;
	}
}
