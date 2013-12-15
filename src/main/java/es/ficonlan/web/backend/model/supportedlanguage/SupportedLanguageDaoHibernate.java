package es.ficonlan.web.backend.model.supportedlanguage;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.model.util.dao.GenericDaoHibernate;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Repository("supportedlanguageDao")
public class SupportedLanguageDaoHibernate extends GenericDaoHibernate<SupportedLanguage,Integer> implements SupportedLanguageDao{}
