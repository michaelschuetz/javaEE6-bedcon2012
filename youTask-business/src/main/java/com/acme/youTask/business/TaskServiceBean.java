package com.acme.youTask.business;

import com.acme.youTask.dao.TaskDao;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class TaskServiceBean implements TaskService {

    @Inject
    TaskDao taskDao;

    // -------------- interface method implementations ------------------------------------

    @Override
    public void saveTask(Task task) {
        taskDao.persist(task);
    }

    @Override
    public Task mergeTask(Task task) {
        return taskDao.merge(task);
    }

    @Override
    public void removeTask(Task task) {
        taskDao.delete(loadTask(task.getId()));
    }

    @Override
    public boolean taskExists(long id) {
        return taskDao.exists(id);
    }

    @Override
    public Task loadTask(long id) {
        return taskDao.load(id);
    }

    @Override
    public List<Task> loadAll() {
        return taskDao.loadAll();
    }

    @Override
    public List<Task> loadForCategory(Category category) {
        return taskDao.findForCategory(category);
    }

    @Override
    public List<Task> loadForUser(User user) {
        return taskDao.findForUser(user);
    }


}
