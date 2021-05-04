package acme.features.manager.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.SpamComponent;
import acme.entities.roles.Gerente;
import acme.entities.tasks.Task;
import acme.entities.words.Word;
import acme.features.administrator.spam.AdministratorSpamRepository;
import acme.features.administrator.word.AdministratorWordRepository;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class ManagerTaskUpdateService implements AbstractUpdateService<Gerente, Task> {

	
	@Autowired
	protected ManagerTaskRepository repository;
	
	@Autowired
	protected AdministratorWordRepository wordSpamRepository;
	
	@Autowired
	protected AdministratorSpamRepository spamRepository;
	
	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		boolean result;
		int taskId;
		Task task;
		Gerente manager;
		Principal principal;

		taskId = request.getModel().getInteger("id");
		task = this.repository.findOneTaskFromId(taskId);
		manager = task.getManager();
		principal = request.getPrincipal();
		result =manager.getUserAccount().getId() == principal.getAccountId();
		return result;
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "title", "startDate", "endDate", "workload", "description", "optionalLink", "visibility", "finished", "executionPeriod");
	}

	@Override
	public Task findOne(final Request<Task> request) {
		assert request != null;
		return this.repository.findOneTaskFromId(request.getModel().getInteger("id"));
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Double threshold = this.spamRepository.findUniqueSpamModule().getThreshold();
		final List<Word> spamWords= this.wordSpamRepository.findMany();
	
		
		if (!errors.hasErrors("title")) {
            errors.state(request,!SpamComponent.containSpam(entity.getTitle(),spamWords,threshold) , "title", "manager.task.form.title.error.spam");
        }
     if (!errors.hasErrors("description")) {
            errors.state(request,!SpamComponent.containSpam(entity.getDescription(),spamWords,threshold) , "description", "manager.task.form.description.error.spam");
        }
     if (!errors.hasErrors("optionalLink")) {
            errors.state(request,!SpamComponent.containSpam(entity.getOptionalLink(),spamWords,threshold) , "optionalLink", "manager.task.form.optionalLink.error.spam");
        }
     
     if (!errors.hasErrors("endDate")) {
         errors.state(request,entity.getStartDate().before(entity.getEndDate()) , "endDate", "manager.task.form.endDate.error");
     }
     if (!errors.hasErrors("workload")) {
         errors.state(request,entity.getWorkload()<=entity.calculateExecutionPeriod() , "workload", "manager.task.form.workload.error");
     }
     
	}

	@Override
	public void update(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;
		
		final Long executionPeriod= entity.calculateExecutionPeriod();
        entity.setExecutionPeriod(executionPeriod);

		this.repository.save(entity);
	}
	
	@Override
	public void onSuccess(final Request<Task> request, final Response<Task> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
