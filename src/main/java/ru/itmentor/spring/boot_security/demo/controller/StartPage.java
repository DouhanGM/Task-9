package ru.itmentor.spring.boot_security.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.itmentor.spring.boot_security.demo.api.response.UserResponse;
import ru.itmentor.spring.boot_security.demo.entity.Person;
import ru.itmentor.spring.boot_security.demo.security.PersonDetails;
import ru.itmentor.spring.boot_security.demo.service.UserDetailsServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class StartPage  {


      public final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public StartPage(UserDetailsServiceImpl personDetailsService) {
        this.userDetailsService = personDetailsService;
    }

    @GetMapping("/admin")
  public String personList(){
        List<Person> people = userDetailsService.allPersons();
        return people.toString();
  }

    @PutMapping("/admin/{id}")
    public RedirectView  personListUpdate(@PathVariable Long id,@RequestBody Person person, RedirectAttributes redirectAttributes) throws Exception {
        Optional<Person> existingPersonOptional = Optional.ofNullable(userDetailsService.findPersonById(id));

        if (!existingPersonOptional.isPresent()) {
            throw new Exception("Пользователь не найден с id " + id);
        }
        Person existingPerson = existingPersonOptional.get();
        existingPerson.setName(person.getName());
        existingPerson.setLastname(person.getLastname());
        existingPerson.setAge(person.getAge());

        userDetailsService.update(existingPerson);
        redirectAttributes.addFlashAttribute("message", "Успешное обновление пользователя!");
        return new RedirectView("/admin");
    }

    @DeleteMapping("/admin/{id}")
    public RedirectView  personDrop(@PathVariable Long id, RedirectAttributes redirectAttributes) throws Exception {
        Optional<Person> existingPersonOptional = Optional.ofNullable(userDetailsService.findPersonById(id));

        if (!existingPersonOptional.isPresent()) {
            throw new Exception("Пользователь не найден с id " + id);
        }

        userDetailsService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "Успешное обновление пользователя!");
        return new RedirectView("/admin");
    }

  @GetMapping("/")
    public String startPage(){

        return "WELCOME";
    }


  @PostMapping("/add")
    public ResponseEntity<HttpStatus> addPerson(@RequestBody Person person) {
        userDetailsService.save(person);
    return new ResponseEntity<>(HttpStatus.OK);
  }

//  @GetMapping("/users")
//  public String getPerson (@AuthenticationPrincipal PersonDetails personDetails){
//Long id =  personDetails.getPerson().getId();
// return userDetailsService.findPersonById(id).toString();
//  }

    @GetMapping("/users")
    public ResponseEntity<UserResponse> personResponseEntity(@AuthenticationPrincipal PersonDetails personDetails){
        Long id =  personDetails.getPerson().getId();
       // return  ResponseEntity;
       return new ResponseEntity<>(UserResponse.builder().name("name").age(25).build(), HttpStatus.OK);
    }
}
