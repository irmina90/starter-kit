package pl.spring.demo.aop;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Sequence;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

@Component
@Aspect
public class BookDaoAdvisor implements MethodBeforeAdvice {
	
	@Override
	public void before(Method method, Object[] objects, Object o) throws Throwable {

		if (hasAnnotation(method, o, NullableId.class)) {
			checkNotNullId(objects[0]);
		}
	}
	
	private void checkNotNullId(Object o) {
		if (o instanceof IdAware && ((IdAware) o).getId() != null) {
			throw new BookNotNullIdException();
		}
	}

	@SuppressWarnings("unchecked")
	private boolean hasAnnotation(Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
		@SuppressWarnings("unchecked")
		boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

		if (!hasAnnotation && o != null) {
			hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes())
					.getAnnotation(annotationClazz) != null;
		}
		return hasAnnotation;
	}

}
