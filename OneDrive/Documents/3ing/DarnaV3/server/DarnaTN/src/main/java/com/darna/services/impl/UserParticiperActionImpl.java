package com.darna.services.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.darna.models.Action;
import com.darna.models.User;
import com.darna.models.UserAction;
import com.darna.models.pk.UserActionKey;
import com.darna.services.UserParticiperActionService;
import com.darna.repository.ActionRepository;
import com.darna.repository.UserParticiperActionRepository;
import com.darna.repository.UserRepository;


@Service
public class UserParticiperActionImpl implements UserParticiperActionService{

	@Autowired
	UserParticiperActionRepository userParticiperAction;
	
	@Autowired
	ActionRepository actionRepository;
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserAction participerAction(Long idU, Long idA) {
		UserActionKey ufkey = new UserActionKey(idU,idA);
		User user = userRepository.findById(idU).get();		
		Action action = actionRepository.findById(idA).get();
		//action.setNbmembre_action(action.getNbmembre_action()-1);
		UserAction userAction = new UserAction(ufkey, action, user,new Date(),false);
		return userParticiperAction.save(userAction);		
		
		
	}



	
	
	@Override
	public UserAction getUserActionById(long idU, long idA) {
		UserActionKey idKey = new UserActionKey(idU,idA);
		return userParticiperAction.findById(idKey).get();
	}


	@Override
	public List<UserAction> getAllUserAction() {
		// TODO Auto-generated method stub
		return (List<UserAction>) userParticiperAction.findAll();
	}





	@Override
	public List<UserAction> findActionByUser(long idU) {
		User user = userRepository.findById(idU).get();
		return userParticiperAction.findActionByUser(user);
	
	}





	@Override
	public List<UserAction> findUserByAction(long idA) {
		// TODO Auto-generated method stub
		Action action = actionRepository.findById(idA).get();
		return userParticiperAction.findUserByAction(action);	}




	@Transactional
	@Override
	public UserAction autoriserParticipation(Long idU, Long idA,Boolean autorisation) {
		UserActionKey UAkey = new UserActionKey(idU,idA);
		UserAction userAction=userParticiperAction.findById(UAkey).get();
		userAction.setAutorisation(autorisation);
		Action action = actionRepository.findById(idA).get();
		if (autorisation==true)
		{
			action.setNbmembre_action(action.getNbmembre_action()-1);
		}
		else if (autorisation==false)
		{
			action.setNbmembre_action(action.getNbmembre_action()+1);
		}
		return userParticiperAction.save(userAction);
	}




	@Transactional
	@Override
	public List<UserAction> getListDemandeParticipation() {
		
		return userParticiperAction.findByAutorisation(false);
	}




	@Transactional
	@Override
	public List<UserAction> getListParticipationAutorise() {
		return userParticiperAction.findByAutorisation(true);
	}
	


}
