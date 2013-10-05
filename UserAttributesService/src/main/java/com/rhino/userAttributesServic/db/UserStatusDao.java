package com.rhino.userAttributesServic.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.codec.Base64;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhino.userAttributesServic.data.UserDataInterface;


@Repository
@Transactional
public class UserStatusDao {
	private Session session;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		
	}
	//String sql = "select v from UserStatus v where v.dataSourceType = 'ExchangeDataCollector' and v.onProcess = false order by v.date";
	//String sql = "select v from UserStatus v , UserStatus v1 where v.dataSourceType='QuestionBuilder' and v1.dataSourceType='ExchangeDataCollector' and v.userID=v1.userID and v.date>v1.date and v1.onProcess=false order by v.date";
	
	public List<UserStatus> getJob(String sql,int size){
		Query query = session.createQuery(sql);
		query.setMaxResults(size);
		@SuppressWarnings("unchecked")
		List<UserStatus> job = query.list();
		for(UserStatus UserStatus:job){
			UserStatus.setOnProcess(true);
			UserStatus.setWorkingDate(System.currentTimeMillis());
			update(UserStatus);
		}
		return job;
	}

	public UserStatus getByuserID(String userID){
		Criteria criteria = session.createCriteria(UserStatus.class);
		criteria.add(Restrictions.ilike("userID", userID));
		@SuppressWarnings("unchecked")
		List<UserStatus> result = criteria.list();
		if(result == null || result.size()==0){
			return null;
		}
		return result.get(0);
	}

	public UserStatus getByTypeAndID(String type,String userID){
		Criteria criteria = session.createCriteria(UserStatus.class);
		criteria.add(Restrictions.ilike("dataSourceType", type));
		criteria.add(Restrictions.ilike("userID", userID));
		@SuppressWarnings("unchecked")
		List<UserStatus> job = criteria.list();
		if(job!= null && job.size()>0){
			return job.get(0);
		}
		return null;
	}

	public boolean exist(String type,String userID){
		Criteria criteria = session.createCriteria(UserStatus.class);
		criteria.add(Restrictions.ilike("dataSourceType", type));
		criteria.add(Restrictions.ilike("userID", userID));
		@SuppressWarnings("unchecked")
		List<UserStatus> job = criteria.list();
		if(job.size()>0){
			return true;
		}
		return false;
	}
	
	public Map<String ,UserStatus> getByID(String id){
		HashMap <String ,UserStatus> map = new HashMap<String ,UserStatus>();
		Criteria criteria = session.createCriteria(UserStatus.class);
		criteria.add(Restrictions.ilike("userID", id));
		@SuppressWarnings("unchecked")
		List<UserStatus> result = criteria.list();
		if(result==null || result.size() ==0){
			return map;
		}
		for(UserStatus user:result){
			map.put(user.getDataSourceType(), user);
		}
		return map;
	}

	public void updateJob(String type,Collection<UserDataInterface>usersID,boolean value,long time) throws IOException{
		for(UserDataInterface user:usersID){
			String data = getSyncData(user.getData());
			updateStatus(type,user.getID(),value,time,data);
		}
	}
	
	public void updateStatus(String type,String userID,boolean status,long time,String data){
		UserStatus user = getByTypeAndID(type,userID);
		user.setOnProcess(status);
		user.setDate(time);
		user.setData(data);
		user.setWorkingDate(time);
		update(user);
	}
		
	public void clearOldJobs(long lookBackTime){
		long time = System.currentTimeMillis()- lookBackTime;
		Query query = session.createQuery("select v from UserStatus v where v.onProcess = true and v.workingDate < "+time);
		
		@SuppressWarnings("unchecked")
		List<UserStatus> job = query.list();
		for(UserStatus UserStatus:job){
			UserStatus.setOnProcess(false);
			UserStatus.setWorkingDate(System.currentTimeMillis());
			update(UserStatus);
		}
	}

	public void update(UserStatus UserStatus){
		session.merge(UserStatus);
	}
	
	public void save(UserStatus UserStatus){
		session.save(UserStatus);
	}

	public int initStatus(boolean status){
		Query query = session.createQuery("update UserStatus set onProcess = :status");
		query.setParameter("status", status);
		return query.executeUpdate();
	}
	
	public String getSyncData(Object data) throws IOException{
		if (data == null){
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(data);
		oos.flush();
		byte[] array = baos.toByteArray();
		array = Base64.encode(array);
		String str = new String(array);
		return str;
	}
	
	public Object getData(String str) throws IOException, ClassNotFoundException{
		if (str == null){
			return null;
		}
		byte[] array = str.getBytes();
		array = Base64.decode(array);
		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object object = ois.readObject();
		return object;
	}
}
