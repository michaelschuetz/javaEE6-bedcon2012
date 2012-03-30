package com.acme.youTask.web.controller;

import com.acme.youTask.business.TaskService;
import com.acme.youTask.business.UserService;
import com.acme.youTask.domain.Task;
import com.acme.youTask.domain.User;
import com.acme.youTask.domain.enums.Category;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.international.status.builder.BundleKey;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Named
@SessionScoped
public class TaskController implements Serializable {

    private static final long serialVersionUID = 1L;

    private Category categoryFilter;

    @Inject
    TaskService taskService;

    @Inject
    UserService userService;

    @Inject
    Messages messages;

    private Task task;

    private boolean hasNewTask;
    private boolean hasTaskEditable;


// -------------- Initialization ------------------------------------------------------

    @PostConstruct
    public void init() {
        task = new Task();
        hasNewTask = false;
        hasTaskEditable = false;
    }


// -------------- Actions -------------------------------------------------------------

    public void saveNewTask() {
        // TODO get currently logged in user!
        final User dummyUser = userService.loadByUsername("xXx");

        // initially new tasks are not finished and not editable
        task.setFinished(false);
        task.setUser(dummyUser);

        taskService.saveTask(task);
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.task.saved" + task.getLabel()));

        setHasNewTask(false);
    }

    public void removeTask() {
        if (hasNewTask) {
            setHasNewTask(false);
        }

        if (taskService.taskExists(task.getId())) {
            taskService.removeTask(task);
            messages.info(new BundleKey("messages", "com.acme.youTask.msg.task.removeSuc" + task.getLabel()));
//            task = null;
            setHasTaskEditable(false);
        } else {
            messages.error(new BundleKey("messages", "com.acme.youTask.msg.task.removeFail" + task.getLabel()));
        }
    }

    public void updateTask() {
        if (taskService.taskExists(task.getId())) {
            taskService.mergeTask(task);
            messages.info(new BundleKey("messages", "com.acme.youTask.msg.task.updateSuc" + task.getLabel()));

            setHasTaskEditable(false);
        } else {
            messages.error(new BundleKey("messages", "com.acme.youTask.msg.task.updateFail" + task.getLabel()));
        }
    }

    public void enableEditMode() {
        setHasTaskEditable(true);
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.task.editEnabled" + task.getLabel()));
    }

    public void disableEditMode() {
        setHasTaskEditable(false);
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.task.editDisabled" + task.getLabel()));
    }


// -------------- Controller Helper ---------------------------------------------------

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<Task>();
        final List<Task> all = taskService.loadAll();

        if (categoryFilter != null) {
            for (final Task task : all) {
                if (task.getCategory() == categoryFilter) {
                    tasks.add(task);
                }
            }
        } else {
            tasks = all;
        }

        return tasks;
    }


// -------------- Getter/Setter -------------------------------------------------------

    public Task getTask() {
        return task;
    }
    public void setTask(Task task) {
        this.task = task;
    }

    public Category getCategoryFilter() {
        return categoryFilter;
    }
    public void setCategoryFilter(final Category categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public boolean getHasNewTask() {
        return hasNewTask;
    }
    public void setHasNewTask(final boolean hasNewTask) {
        this.hasNewTask = hasNewTask;

        if (hasNewTask) {
            task = new Task();
            setHasTaskEditable(false);
        }
    }

    public boolean getHasTaskEditable() {
        return hasTaskEditable;
    }
    public void setHasTaskEditable(final boolean hasTaskEditable) {
        this.hasTaskEditable = hasTaskEditable;

        if (hasTaskEditable) {
            setHasNewTask(false);
        } else {
            task = new Task();
        }
    }

    public boolean getIsTaskEditable(final Task task) {
        return getHasTaskEditable() && (this.task.getId().equals(task.getId()));
    }


// -------------- Listener ------------------------------------------------------------

    public void filterChanged(final ValueChangeEvent event) {
        categoryFilter = (Category) event.getNewValue();
        messages.info(new BundleKey("messages", "com.acme.youTask.msg.task.filterChanged" + categoryFilter));
    }
}