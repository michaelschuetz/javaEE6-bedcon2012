package com.acme.youTask.dao;

import com.acme.youTask.dao.common.GenericDao;
import com.acme.youTask.domain.User;

import javax.ejb.Local;

/**
 * @author <a href="mailto:marek.i@gmx.net">Marek Iwaszkiewicz</a>
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Local
public interface UserDao extends GenericDao<User> {

  User loadByUsername(final String username);

}
