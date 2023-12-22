package com.monocept.insurance.service;


/*
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.AllAgentDto;
import com.monocept.insurance.entities.Agent;
import com.monocept.insurance.entities.Customer;
import com.monocept.insurance.entities.PolicyRegistration;
import com.monocept.insurance.entities.Role;
import com.monocept.insurance.entities.User;
import com.monocept.insurance.exception.ListEmptyException;
import com.monocept.insurance.repository.AgentRepo;
import com.monocept.insurance.repository.PolicyRegistrationRepo;
import com.monocept.insurance.repository.RoleRepo;

import jakarta.transaction.Transactional;

@Service
public class AgentServiceImpl implements AgentService{
	

	
	@Autowired
	private AgentRepo agentRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Autowired
	private PolicyRegistrationRepo policyRegistrationRepo;
	
	
	private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
	
	
	
	@Override
	@Transactional
   public String saveAgent(Agent agent) {
    User user= agent.getUser();
    Optional<Role>getRole=roleRepo.findById(3);
    Role role= getRole.get();
    user.setRole(role);
    userService.saveUser(user);
     agentRepo.save(agent);
     return ("Agent Saved");
      
     }
	
	@Override
	public Page<AllAgentDto> getAllAgents(int pageno, int pagesize) {
		List<AllAgentDto> allAgents = new ArrayList<>();
		List<Agent> agents = agentRepo.findAll();
		if(agents.size()== 0) {
			logger.error("List is empty");
			throw new ListEmptyException("List is empty", HttpStatus.NOT_FOUND);
		}
		logger.info("Agents fetched successfully");
		for(Agent agent: agents) {
			allAgents.add(new AllAgentDto(agent.getAgentid(),agent.getUser().getUsername(),
					agent.getFirstname(),agent.getLastname(),agent.getCommission()));
		}
		pagesize = (pagesize<allAgents.size())?pagesize:allAgents.size();
		if(pagesize == allAgents.size())
			pageno = 0;
		
		
		Pageable pageRequest = PageRequest.of(pageno, pagesize);

		int start = (int) pageRequest.getOffset();
	    int end = Math.min((start + pageRequest.getPageSize()), allAgents.size());

	    List<AllAgentDto> pageContent = allAgents.subList(start, end);
	    return new PageImpl<>(pageContent, pageRequest, allAgents.size());
	}
	
	@Override
	@Transactional
	public String deleteAgent(int agentid) {
		agentRepo.deleteById(agentid);
		return "Agent deleted successfully";
	}
	
	@Override
    @Transactional
    public String updateAgent(int agentId, Agent updatedAgent) {
        Optional<Agent> optionalAgent = agentRepo.findById(agentId);
        if (optionalAgent.isPresent()) {
            Agent existingAgent = optionalAgent.get();

            existingAgent.setFirstname(updatedAgent.getFirstname());
            existingAgent.setLastname(updatedAgent.getLastname());
            existingAgent.setCommission(updatedAgent.getCommission());

            User user = existingAgent.getUser();
            User updatedUser = updatedAgent.getUser();
            user.setUsername(updatedUser.getUsername());
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

            agentRepo.save(existingAgent);
            logger.info("Agent updated successfully");
            return "Agent updated successfully";
        } else {
        	logger.error("Agent not found with id: " + agentId);
        	return "No Agent Found with this Id";
           // throw new EntityNotFoundException("Agent not found with ID: " + agentId);
        }
    }
	
	
	@Override
    public int getCustomersAndCountByUsername(String username) {
        Agent agent = agentRepo.findByUsername(username);
        List<Customer> customers = agent.getCustomers();
        int customerCount = customers.size();
        logger.info (customerCount +" customers for this Agent" );
        return customerCount;
    }
	
	

	@Override
    public double getAgentCommission(String username) {
        Agent agent = agentRepo.findByUsername(username);
        double commission= agent.getCommission();
        logger.info ("commission for this agent "+ commission);
        return commission;
    }
	
	
	@Override
	public int getAgentPolicyCount(String username) {
		int count=0;
		Agent agent = agentRepo.findByUsername(username);
		int agentid= agent.getAgentid();
		List<PolicyRegistration>policyRegistrations= policyRegistrationRepo.findAll();
		for(PolicyRegistration policyRegistration: policyRegistrations) {
			if(policyRegistration.getAgentid()== agentid)
				count ++;
		}
		return count;
	}
	
	
    @Override
    public long getTotalAgentCount() {
    	logger.info("Total Agents:" + agentRepo.count());
        return agentRepo.count();
    }
	
}


*/





import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.AllAgentDto;
import com.monocept.insurance.dto.PolicyRegistrationDto;
import com.monocept.insurance.entities.Agent;
import com.monocept.insurance.entities.Customer;
import com.monocept.insurance.entities.PolicyRegistration;
import com.monocept.insurance.entities.Role;
import com.monocept.insurance.entities.User;
import com.monocept.insurance.exception.ListEmptyException;
import com.monocept.insurance.repository.AgentRepo;
import com.monocept.insurance.repository.CustomerRepo;
import com.monocept.insurance.repository.PolicyRegistrationRepo;
import com.monocept.insurance.repository.RoleRepo;
import com.monocept.insurance.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class AgentServiceImpl implements AgentService{
	

	
	@Autowired
	private AgentRepo agentRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PolicyRegistrationRepo policyRegistrationRepo;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);
	
	
	
	@Override
	@Transactional
   public String saveAgent(Agent agent) {
    User user= agent.getUser();
    Optional<Role>getRole=roleRepo.findById(3);
    Role role= getRole.get();
    user.setRole(role);
    userService.saveUser(user);
     agentRepo.save(agent);
     return ("Agent Saved");
      
     }
	
	@Override
	public Page<AllAgentDto> getAllAgents(int pageno, int pagesize) {
		List<AllAgentDto> allAgents = new ArrayList<>();
		List<Agent> agents = agentRepo.findAll();
		if(agents.size()== 0) {
			logger.error("List is empty");
			throw new ListEmptyException("List is empty", HttpStatus.NOT_FOUND);
		}
		logger.info("Agents fetched successfully");
		for(Agent agent: agents) {
			allAgents.add(new AllAgentDto(agent.getAgentid(),agent.getUser().getUsername(),
					agent.getFirstname(),agent.getLastname(),Math.ceil(agent.getCommission())));
		}
		pagesize = (pagesize<allAgents.size())?pagesize:allAgents.size();
		if(pagesize == allAgents.size())
			pageno = 0;
		
		
		Pageable pageRequest = PageRequest.of(pageno, pagesize);

		int start = (int) pageRequest.getOffset();
	    int end = Math.min((start + pageRequest.getPageSize()), allAgents.size());

	    List<AllAgentDto> pageContent = allAgents.subList(start, end);
	    return new PageImpl<>(pageContent, pageRequest, allAgents.size());
	}
	
	@Override
	public String deleteAgent(int agentid) {
		agentRepo.deleteById(agentid);
		return "Agent deleted successfully";
	}

	@Override
	public String saveCustomer(Customer customer, String username) {
		Customer cust = customerService.saveCustomer(customer);
		List<Agent> agents = agentRepo.findAll();
		Agent agent = null;
		for(Agent a : agents) {
			if(a.getUser().getUsername().equals(username)) {
				agent = a;
				break;
			}
		}
		List<Customer> customers = agent.getCustomers();
		customers.add(cust);
		agent.setCustomers(customers);
		agentRepo.save(agent);
		LocalDate currentDate = LocalDate.now();
		Date date = Date.valueOf(currentDate);
		PolicyRegistration policyRegistration = new PolicyRegistration(agent.getAgentid(),cust.getCustomerid(),0,date);
		policyRegistrationRepo.save(policyRegistration);
		return null;
	}

	@Override
	public Page<PolicyRegistrationDto> getCustomers(String username, int pageno, int pagesize) {
		List<Agent> agents = agentRepo.findAll();
		Agent agent = null;
		for(Agent a : agents) {
			if(a.getUser().getUsername().equals(username)) {
				agent = a;
				break;
			}
		}
		List<PolicyRegistration> data = policyRegistrationRepo.findAll();
		List<PolicyRegistrationDto> result = new ArrayList<>();
		
		for(PolicyRegistration p : data) {
			if(p.getAgentid() == agent.getAgentid()) {
				Customer customer = customerRepo.findById(p.getCustomerid()).get();
				result.add(new PolicyRegistrationDto(p.getCustomerid(),customer.getUser().getUsername(),
						customer.getFirstname(),p.getPolicynumber(),customer.getDocumentstatus()));
			}
		}
		if(result.size()==0) {
			throw new ListEmptyException("List is Empty",HttpStatus.NOT_FOUND);
		}
		
		pagesize = (pagesize<result.size())?pagesize:result.size();
		if(pagesize == result.size())
			pageno = 0;
		
		Pageable pageRequest = PageRequest.of(pageno, pagesize);

	    int start = (int) pageRequest.getOffset();
	    int end = Math.min((start + pageRequest.getPageSize()), result.size());

	    List<PolicyRegistrationDto> pageContent = result.subList(start, end);
	    return new PageImpl<>(pageContent, pageRequest, result.size());

	}

	@Override
	public String updateAgent(int agentid, Agent agent) {
		Agent a = agentRepo.findById(agentid).get();
		a.setFirstname(agent.getFirstname());
		a.setLastname(agent.getLastname());
		agentRepo.save(a);
		return "Profile Updated Successfully";
	}

	@Override
	public Agent getAgent(String username) {
		List<Agent> agents = agentRepo.findAll();
		Agent agent = null;
		for(Agent a : agents) {
			if(a.getUser().getUsername().equals(username)) {
				agent = a;
				break;
			}
		}
		return agent;
	}
	
	@Override
    public double getAgentCommission(String username) {
        Agent agent = agentRepo.findByUsername(username);
        double commission= agent.getCommission();
        logger.info ("commission for this agent "+ commission);
        return Math.ceil(commission);
    }
	
	@Override
    public int getCustomersAndCountByUsername(String username) {
        Agent agent = agentRepo.findByUsername(username);
        List<Customer> customers = agent.getCustomers();
        int customerCount = customers.size();
        logger.info (customerCount +" customers for this Agent" );
        return customerCount;
    }
	
	@Override
	public int getAgentPolicyCount(String username) {
		int count=0;
		Agent agent = agentRepo.findByUsername(username);
		int agentid= agent.getAgentid();
		List<PolicyRegistration>policyRegistrations= policyRegistrationRepo.findAll();
		for(PolicyRegistration policyRegistration: policyRegistrations) {
			if(policyRegistration.getAgentid()== agentid) {
				if(policyRegistration.getPolicynumber() != 0) {
					count++;
				}
			}
				//count ++;
		}
		return count;
	}

	@Override
	public String updatePassword(int agentid, String password) {
		Agent agent = agentRepo.findById(agentid).get();
		User user = agent.getUser();
		user.setPassword(passwordEncoder.encode(password));
		userRepo.save(user);
		return "Password Updated Successfully";
	}

	@Override
	public String sendMail(String[] mails, String text) {
		 // Sender's email details
     
		
		String senderEmail = "ritiksaxena037@gmail.com";
        
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        // Create a session with the sender's credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
          
            	return new PasswordAuthentication("ritiksaxena037@gmail.com","umvlxpvxjpfovuju");
            }
        });

        try {
            for (String recipientEmail : mails) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject("Regarding new Policy Market policy");
                message.setText(text);

                // Send the email
                Transport.send(message);
                System.out.println("Email sent to: " + recipientEmail);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Mail Sent Successfully";
	}

	@Override
    public long getTotalAgentCount() {
    	logger.info("Total Agents:" + agentRepo.count());
        return agentRepo.count();
    }
     

}

