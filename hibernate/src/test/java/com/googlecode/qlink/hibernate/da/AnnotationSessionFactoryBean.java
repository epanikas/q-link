package com.googlecode.qlink.hibernate.da;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContextException;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class AnnotationSessionFactoryBean
	extends LocalSessionFactoryBean
{

	private static final Logger LOG = Logger.getLogger(AnnotationSessionFactoryBean.class);

	private List<String> annotatedClasses;

	public void setAnnotatedClasses(List<String> classes)
	{
		annotatedClasses = classes;
	}

	@Override
	protected void postProcessConfiguration(Configuration config)
		throws HibernateException
	{
		super.postProcessConfiguration(config);

		if (!(config instanceof AnnotationConfiguration)) {
			throw new ApplicationContextException(
				"The configuration must be AnnotationConfiguration.");
		}

		if (annotatedClasses == null) {
			LOG.info("No annotated classes to register with Hibernate.");
			return;
		}

		for (String className : annotatedClasses) {
			try {
				Class clazz = config.getClass().getClassLoader().loadClass(className);
				((AnnotationConfiguration) config).addAnnotatedClass(clazz);

				if (LOG.isDebugEnabled()) {
					LOG.debug("Class " + className + " added to Hibernate config.");
				}
			}
			catch (MappingException e) {
				throw new ApplicationContextException("Unable to register class " + className, e);
			}
			catch (ClassNotFoundException e) {
				throw new ApplicationContextException("Unable to register class " + className, e);
			}
		}
	}
}