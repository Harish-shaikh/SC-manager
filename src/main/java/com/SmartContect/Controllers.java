package com.SmartContect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.SmartContect.SessionHelper.Message;
import com.SmartContect.dao.UserRepos;
import com.SmartContect.entities.UserEntity;
import jakarta.servlet.http.HttpSession;

@Controller
public class Controllers {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepos userRepos;

	@GetMapping("/")
	public String home(Model m) {
		m.addAttribute("title", "home page");
		return "home";
	}

	@GetMapping("/about/")
	public String about(Model m) {
		m.addAttribute("title", "about page");
		return "about";
	}

	@GetMapping("/signup/")
	public String sighUp(Model m) {
		m.addAttribute("title", "Signup page");
		m.addAttribute("data", new UserEntity());
		return "signup";
	}

	@PostMapping("/do_register")
	public String registerUser(@ModelAttribute("data") UserEntity ue,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {
		try {
			if (!agreement) {
				System.out.println("you have not agreed the term and conditions");
				throw new Exception("you have not agreed the term and conditions");
			}

			ue.setImageUrl("default.png");
			ue.setRole("USER");
			ue.setEnabled(true);
			ue.setPassword(bCryptPasswordEncoder.encode(ue.getPassword()));
			this.userRepos.save(ue);
			model.addAttribute("data", new UserEntity());
			session.setAttribute("message", new Message("SuccessFully register ", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("data", ue);
			session.setAttribute("message", new Message("SomeThing went wrong!!" + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/admin/")
	public String admin(Model m) {
		m.addAttribute("title", "this is user dasdboard");
		return "admin";

	}

}
