package pl.spring.demo.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.IdAware;

@Component
@Aspect
public class BookDaoAdvisor  {
	
	private BookDao bookDao;
	private Sequence sequence;
	
	@Before("execution(* pl.spring.demo.dao.impl.BookDaoImpl.save(..))")
	public void checkBookId(JoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();	
		
		Object object = joinPoint.getArgs()[0];
		
		if (hasAnnotation(method, joinPoint.getTarget(), NullableId.class)) {
			checkNotNullId(object);
			setBookId(object);
		}	
	}
	
	private void setBookId(Object o) {
		if (o instanceof BookEntity && ((BookEntity) o).getId() == null) {
			((BookEntity) o).setId(sequence.nextValue(bookDao.findAll()));
		}
	}
	
	private void checkNotNullId(Object o) {
		if (o instanceof IdAware && ((IdAware) o).getId() != null) {
			throw new BookNotNullIdException();
		}
	}

	@SuppressWarnings("unchecked")
	private boolean hasAnnotation(Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
		boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

		if (!hasAnnotation && o != null) {
			hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes())
					.getAnnotation(annotationClazz) != null;
		}
		return hasAnnotation;
	}
	
    @Autowired
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    
    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

}
