package com.example.controllers;//Created by KevinBozic on 3/10/16.

import com.example.entities.ClosetContents;
import com.example.entities.User;
import com.example.services.ClosetContentsRepository;
import com.example.services.UserRepository;
import com.example.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ClosetController {
    @Autowired
    UserRepository users;

    @Autowired
    ClosetContentsRepository closetContents;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, Integer page) {
        page = (page == null) ? 0 : page;
        PageRequest pr = new PageRequest(page, 3);
        Page<ClosetContents> p;

        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);

        p = closetContents.findAll(pr);

        model.addAttribute("username", username);
        model.addAttribute("closetContents", closetContents.findByUser(user, pr)); // same as select * from

        model.addAttribute("nextPage", page + 1);
        model.addAttribute("previousPage", page - 1);
        model.addAttribute("showNext", p.hasNext());
        model.addAttribute("showPrevious", p.hasNext());

        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findByName(username);
        if (user == null) { // if user is null
            user = new User(); // creating a new User object
            user.name = username; // assigning whatever is entered into the the username field to user.name
            user.password = PasswordStorage.createHash(password); // hashing/securing whatever's entered into the password field and assigning it to user.password
            users.save(user); // this stores the user object into the table 'users' (in this case, user) in the database
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())) { // else if the password entered isn't associated with the corresponding user...
            throw new Exception("Incorrect password"); // ...throw an Exception
        }

        session.setAttribute("username", username); // taking the username variable from what's entered into the "username" text field and storing it in the session
        return "redirect:/"; // this redirect's to the home page
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/addContents", method = RequestMethod.POST)
    public String addContents(HttpSession session, String item, String color, int amount) {
        String username = (String) session.getAttribute("username");
        ClosetContents contents = new ClosetContents(item, color, amount);
        contents.user = users.findByName(username);
        closetContents.save(contents);
        return "redirect:/";
    }

    @RequestMapping(path = "/deleteContents", method = RequestMethod.POST)
    public String deleteContents(HttpSession session, int id) {
        closetContents.delete(id);
        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String editContents(Model model, int id) {
        model.addAttribute("content", closetContents.findOne(id));
        return "edit";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String newEditContents(String item, String color, int amount, int id) {
        ClosetContents contents = closetContents.findOne(id);
        contents.item = item;
        contents.color = color;
        contents.amount = amount;
        closetContents.save(contents);
        return "redirect:/";
    }
}
