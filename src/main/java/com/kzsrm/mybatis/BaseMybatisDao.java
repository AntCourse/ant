package com.kzsrm.mybatis;
import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class BaseMybatisDao<E,PK extends Serializable> extends MybatisDao  implements EntityDao<E,PK> {
   
	private static Logger logger = LoggerFactory.getLogger(BaseMybatisDao.class);
	
    public E getById(PK primaryKey) {
        return (E)getSqlSession().selectOne(getFindByPrimaryKeyStatement(), primaryKey);
    }
    
	public void deleteById(PK id) {
		int affectCount = getSqlSession().delete(getDeleteStatement(), id);
	}
	
    public void save(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = getSqlSession().insert(getInsertStatement(), entity);    	
    }
    
	public void update(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = getSqlSession().update(getUpdateStatement(), entity);
	}
	
	
	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * @param o
	 */
    protected void prepareObjectForSaveOrUpdate(E o) {
    			logger.info("prepareObjectForSaveOrUpdate...");
    			// System.out.println("kw...");
    }

    /*public String getMybatisMapperNamesapce() {
        throw new RuntimeException("not yet implement");
    }*/
    
    public abstract String getMybatisMapperNamesapce();
    
    public String getFindByPrimaryKeyStatement() {
    	System.out.println("getMybatisMapperNamesapce()   "+getMybatisMapperNamesapce());
        return getMybatisMapperNamesapce()+".getById";
    }

    public String getInsertStatement() {
    	System.out.println("getMybatisMapperNamesapce()   "+getMybatisMapperNamesapce());
        return getMybatisMapperNamesapce()+".insert";
    }

    public String getUpdateStatement() {
    	System.out.println("getMybatisMapperNamesapce()   "+getMybatisMapperNamesapce());
    		return getMybatisMapperNamesapce()+".updateById";
    }

    public String getDeleteStatement() {
    	System.out.println("getMybatisMapperNamesapce()   "+getMybatisMapperNamesapce());
    		return getMybatisMapperNamesapce()+".deleteById";
    }
    
    public String getFindStatement() {
    	System.out.println("getMybatisMapperNamesapce()   "+getMybatisMapperNamesapce());
    		return getMybatisMapperNamesapce()+".findAll";
    }
   
	public List<E> findAll() {
		// throw new UnsupportedOperationException();
		String statementName = getFindStatement();
		return getSqlSession().selectList(statementName);
	}

	
	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}
	
	public void flush() {
		//ignore
	}
	
}
