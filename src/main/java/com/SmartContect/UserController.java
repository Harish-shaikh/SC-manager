package com.SmartContect;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.SmartContect.SessionHelper.Message;
import com.SmartContect.dao.ContactRepos;
import com.SmartContect.dao.UserRepos;
import com.SmartContect.entities.ContectEntity;
import com.SmartContect.entities.UserEntity;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepos ur;
	@Autowired
	private ContactRepos cr;

	// add comman data to all htmlpage
	@ModelAttribute
	public void addCommanData(Model m, Principal p) {
		String username = p.getName();
		UserEntity userEntity = ur.userdetails(username);
		m.addAttribute("userdata", userEntity);
	}

	@GetMapping("/index")
	public String loginForm(Model m) {
		m.addAttribute("title", "this is UserPage");
		return "User/User";
	}

	// addcontact handller
	@GetMapping("/add-contact")
	public String openAddContactForm(Model m) {
		m.addAttribute("title", "AddContactFrom");
		m.addAttribute("contact", new ContectEntity());
		return "User/add_contact_form";
	}

	// processing at contact form

	@PostMapping("/save-contact")
	public String saveAddContactForm(@ModelAttribute ContectEntity ce, @RequestParam("profileImage") MultipartFile file,
			Principal p, HttpSession hp) {

		try {
			// get name who are login
			String name = p.getName();

			// find name in database
			UserEntity userEntity = this.ur.userdetails(name);

			// processing and uploadtning file
			if (file.isEmpty()) {
				// if file is empty then type your message
				System.out.println("file is empty");
				ce.setImage("project1.png");
			} else {
				// get file name
				ce.setImage(file.getOriginalFilename());

				// set folder path and save
				File savefile = new ClassPathResource("static/image").getFile();

				// save and copy
				Files.copy(file.getInputStream(),
						Paths.get(savefile.getAbsolutePath() + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);

			}

			// assign userentity to contact
			ce.setUserEntity(userEntity);

			// add data into contect entity
			userEntity.getContectEntity().add(ce);

			// save data into database
			this.ur.save(userEntity);

			hp.setAttribute("message", new Message("your contect successfully added", "alert-success"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			hp.setAttribute("message", new Message("something went wrong", "alert-danger"));
		}
		return "User/add_contact_form";
	}

	// show contacts handler
	// per page =5{n}
	// Current page = 0[page]
	// {page} is give for pagination technic
	@GetMapping("/{page}/show-contact")
	public String showContact(@PathVariable("page") Integer page, Model m, Principal p) {

		String userName = p.getName();
		UserEntity userEntity = this.ur.userdetails(userName);

		// find contact by userId
		Pageable pageable = PageRequest.of(page, 4);
		Page<ContectEntity> contacts = this.cr.findContactByUser(userEntity.getuId(), pageable);
		m.addAttribute("Contacts", contacts);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpage", contacts.getTotalPages());

		return "User/show_contact";
	}

	// show-contact-detail handeller
	@GetMapping("/{cId}/show-contact-detail")
	public String contactDetail(@PathVariable("cId") Integer cId, Model m, Principal p) {
		ContectEntity contectEntity = this.cr.findById(cId).get();

		String username = p.getName();
		UserEntity userEntity = this.ur.userdetails(username);
		if (userEntity.getuId() == contectEntity.getUserEntity().getuId())
			m.addAttribute("contectdetail", contectEntity);

		return "User/show-contact-detail";
	}

	// delete contact
	@GetMapping("/deletes/{cId}")
	public String deletedata(@PathVariable("cId") Integer cId, Model m, Principal p, HttpSession hp) {
		ContectEntity contectEntity = this.cr.findById(cId).get();

		String username = p.getName();
		UserEntity userEntity = this.ur.userdetails(username);
		if (userEntity.getuId() == contectEntity.getUserEntity().getuId()) {
			contectEntity.setUserEntity(null);

			this.cr.delete(contectEntity);
			hp.setAttribute("message", new Message("Contact deleted succesfully...", "alert-success"));
		}

		return "redirect:/user/0/show-contact";
	}
	
	//open update contrller handller
	@PostMapping("/update/{cId}")
	public String updateData(@PathVariable("cId") Integer cId,  Model m,Principal p) {
		ContectEntity contectEntity=this.cr.findById(cId).get();
		
		m.addAttribute("title", "update data");
		String username=p.getName();
		UserEntity userEntity=this.ur.userdetails(username);
		
		m.addAttribute("contacts", contectEntity);
		
		return "User/update";
	}
	//update contect handeller
	@PostMapping("/process-update")
	public String updateHandeller(@ModelAttribute ContectEntity ce,@RequestParam("profileImage") MultipartFile file,Principal p,Model m,HttpSession hp) {
		
		try {
			//old contact details
	         ContectEntity	oldContectDetail =this.cr.findById(ce.getcId()).get();
			
			//image
			if(!file.isEmpty()) {
				//delete old photo
				File deleteFile=new ClassPathResource("static/image").getFile();
				File file1=new File(deleteFile, oldContectDetail.getProfileImage());
				file1.delete();
				
				//update photo
				File saveFile=new ClassPathResource("static/image").getFile();
				Files.copy(file.getInputStream(),
						Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				
			          ce.setImage(file.getOriginalFilename());
			}
			else {
				ce.setImage(oldContectDetail.getName());
			}
			UserEntity userEntity=this.ur.userdetails(p.getName());
			ce.setUserEntity(userEntity);
			
				this.cr.save(ce);
				
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/user/"+ce.getcId()+ "/show-contact";
	}

}
