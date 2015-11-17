package es.ficonlan.web.backend.dao;

import org.springframework.stereotype.Repository;

import es.ficonlan.web.backend.entities.SupportedLanguage;

/**
 * @author Daniel Gómez Silva
 * @version 1.0
 */
@Repository("supportedlanguageDao")
public class SupportedLanguageDaoHibernate extends GenericDaoHibernate<SupportedLanguage,Integer> implements SupportedLanguageDao{}
