/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(t) from Task t")
	Integer totalNumberOfTasks();
	
	@Query("select count(t) from Task t where t.visibility = 'Public'")
	Integer totalNumberOfPublicTasks();
	
//	@Query("select count(t) from Task t where t.visibility = 'Private'")
//	Integer totalNumberOfPrivateTasks();
	
	@Query("select count(t) from Task t where t.endDate < CURRENT_TIMESTAMP")
	Integer totalNumberOfFinishedTasks();
	
	@Query("select avg(t.workload) from Task t")
	Double averageTaskWorkload();
	
	@Query("select stddev(t.workload) from Task t")
	Double deviationTaskWorkload();
	
	@Query("select min(t.workload) from Task t")
	Double minTaskWorkload();
	
	@Query("select max(t.workload) from Task t")
	Double maxTaskWorkload();
	
	@Query("select avg(t.executionPeriod) from Task t")
	Long averageTaskExecutionPeriod();
	
	@Query("select stddev(t.executionPeriod) from Task t")
	Long deviationTaskExecutionPeriod();
	
	@Query("select min(t.executionPeriod) from Task t")
	Long minTaskExecutionPeriod();
	
	@Query("select max(t.executionPeriod) from Task t")
	Long maxTaskExecutionPeriod();
//
//	@Query("select avg(select count(a) from Application a where a.worker.id = w.id) from Worker w")
//	Double averageNumberOfApplicationsPerWorker();
//
//	@Query("select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id = e.id and a.job.id = j.id)) from Employer e")
//	Double averageNumberOfApplicationsPerEmployer();
//
//	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.PENDING")
//	Double ratioOfPendingApplications();
//
//	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.ACCEPTED")
//	Double ratioOfAcceptedApplications();
//
//	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.ApplicationStatus.REJECTED")
//	Double ratioOfRejectedApplications();

}
