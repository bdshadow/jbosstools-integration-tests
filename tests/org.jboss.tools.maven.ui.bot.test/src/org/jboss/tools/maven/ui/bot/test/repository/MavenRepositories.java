package org.jboss.tools.maven.ui.bot.test.repository;


import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.hasItem;

import java.util.Arrays;
import java.util.List;

import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.jboss.tools.maven.reddeer.maven.ui.preferences.ConfiguratorPreferencePage;
import org.jboss.tools.maven.reddeer.wizards.ConfigureMavenRepositoriesWizard;
import org.jboss.tools.maven.ui.bot.test.AbstractMavenSWTBotTest;
import org.jboss.tools.maven.ui.bot.test.utils.RepositoryExists;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MavenRepositories extends AbstractMavenSWTBotTest{
	
	
	private static final String EAP_REPO = "redhat-ga-repository";
	private static final String JBOSS_REPO = "jboss-public-repository";
	private static final String BINTRAY = "bintray";
	private static final String SPRINGSOURCE_EXTERNAL = "com-springsource-repository-bundles-external";
	private static final String SPRINGSOURCE = "com-springsource-repository-bundles-release";
	private static final String JAVANET = "java-net-public";
	private static final String APACHE = "repository-apache-org";
	private static final String EMPTY = "";
	
	@BeforeClass
	public static void setup(){
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConfiguratorPreferencePage jm = new ConfiguratorPreferencePage();
		preferenceDialog.select(jm);
		ConfigureMavenRepositoriesWizard mr = jm.configureRepositories();
		boolean deleted = mr.removeAllRepos();
		if(deleted){
			mr.confirm();
		} else {
			mr.cancel();
		}
		preferenceDialog.ok();
	}
	
	@AfterClass
	public static void clean(){
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConfiguratorPreferencePage jm = new ConfiguratorPreferencePage();
		preferenceDialog.select(jm);
		ConfigureMavenRepositoriesWizard mr = jm.configureRepositories();
		boolean deleted = mr.removeAllRepos();
		if(deleted){
			mr.confirm();
		} else {
			mr.cancel();
		}
		preferenceDialog.ok();
	}
	
	@Test
	public void checkPredefinedRepositories(){
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConfiguratorPreferencePage jm = new ConfiguratorPreferencePage();
		preferenceDialog.select(jm);
		ConfigureMavenRepositoriesWizard mr = jm.configureRepositories();
		List<String> repositories = mr.getRepositoriesList();
		List<String> expected = Arrays.asList(EAP_REPO,JBOSS_REPO,BINTRAY,SPRINGSOURCE,SPRINGSOURCE_EXTERNAL,JAVANET,APACHE, EMPTY);
		mr.cancel();
		preferenceDialog.cancel();
		for(String ex: expected){
			assertThat(ex+" repository is missing in wizard choices",repositories, hasItem(ex));
		}
		assertEquals(expected.size(),repositories.size());
		
	}
	
	@Test
	public void modifyEAPRepo(){
		String repoId = addRepo(EAP_REPO);
		assertTrue("EAP Repository is missing in Maven repositories view", new RepositoryExists(EAP_REPO).test());
		
		editRepo(repoId);
		assertTrue("EAP Repository is present in Maven repositories view", new RepositoryExists(EAP_REPO).test());
		
		removeRepo(repoId+" (Inactive)");
		assertFalse("EAP Repository is still present in Maven repositories view", new RepositoryExists(EAP_REPO).test());
	}

	@Test
	public void modifyJBossRepo(){
		String repoId = addRepo(JBOSS_REPO);
		assertTrue("JBoss Repository is missing in Maven repositories view", new RepositoryExists(JBOSS_REPO).test());
		
		editRepo(repoId);
		assertTrue("JBOSS Repository is present in Maven repositories view", new RepositoryExists(JBOSS_REPO).test());
		
		removeRepo(repoId+" (Inactive)");
		assertFalse("JBoss Repository is missing in Maven repositories view", new RepositoryExists(JBOSS_REPO).test());
	}
	
	public String addRepo(String repo){
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConfiguratorPreferencePage jm = new ConfiguratorPreferencePage();
		preferenceDialog.select(jm);
		ConfigureMavenRepositoriesWizard mr = jm.configureRepositories();
		String repoId = mr.chooseRepositoryFromList(repo,true,false);
		mr.confirm();
		jm.apply();
		preferenceDialog.ok();
		return repoId;
	}
	
	public void editRepo(String repoId){
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConfiguratorPreferencePage jm = new ConfiguratorPreferencePage();
		preferenceDialog.select(jm);
		ConfigureMavenRepositoriesWizard mr = jm.configureRepositories();
		mr.editRepo(repoId, false, null, null, null);
		mr.confirm();
		jm.apply();
		preferenceDialog.ok();
	}
	
	public void removeRepo(String repoId){
		WorkbenchPreferenceDialog preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		ConfiguratorPreferencePage jm = new ConfiguratorPreferencePage();
		preferenceDialog.select(jm);
		ConfigureMavenRepositoriesWizard mr = jm.configureRepositories();
		mr.removeRepo(repoId);
		mr.confirm();
		preferenceDialog.ok();
	}

}
