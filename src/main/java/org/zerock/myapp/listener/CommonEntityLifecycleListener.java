package org.zerock.myapp.listener;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor
public class CommonEntityLifecycleListener {
	
	
	@PrePersist
	void prePersist(Object entity) {
		log.trace("prePersist({}) invoked.", entity);
	} // prePersist
	
	@PostPersist
	void postPersist(Object entity) {
		log.trace("postPersist({}) invoked.", entity);
	} // postPersist
	
	@PreUpdate
	void preUpdate(Object entity) {
		log.trace("preUpdate({}) invoked.", entity);
	} // preUpdate
	
	@PostUpdate
	void postUpdate(Object entity) {
		log.trace("postUpdate({}) invoked.", entity);
	} // postUpdate
	
	@PreRemove
	void preRemove(Object entity) {
		log.trace("preRemove({}) invoked.", entity);
	} // preRemove
	
	@PostRemove
	void postRemove(Object entity) {
		log.trace("postRemove({}) invoked.", entity);
	} // postRemove
	
	@PostLoad
	void postLoad(Object entity) {
		log.trace("postLoad({}) invoked.", entity);
	} // postLoad

} // end class
