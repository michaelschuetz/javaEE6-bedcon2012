package com.acme.youTask.business;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Local
public interface TaskService extends Serializable {

    void saveTask(final Task task);

    Task mergeTask(final Task task);

    void removeTask(final Task task);

    boolean taskExists(final long id);

    Task loadTask(final long id);

    List<Task> loadAll();

    List<Task> loadForCategory(final Category category);

    List<Task> loadForUser(final User user);

}
