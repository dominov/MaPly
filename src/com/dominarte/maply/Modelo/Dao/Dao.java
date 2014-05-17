package com.dominarte.maply.Modelo.Dao;

import java.util.concurrent.ExecutionException;

/**
 * Created by Cristian on 6/04/14.
 */
public interface Dao {

	Object update(Object o);

	void delete(Object o);

	Object listar(Object o) throws ExecutionException, InterruptedException;

	Object find(Object o);
}
