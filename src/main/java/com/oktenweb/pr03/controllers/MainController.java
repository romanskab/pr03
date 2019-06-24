package com.oktenweb.pr03.controllers;

import com.oktenweb.pr03.dao.DocumentDAO;
import com.oktenweb.pr03.entity.Document;
import com.oktenweb.pr03.entity.Type;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@AllArgsConstructor
public class MainController {

    private DocumentDAO documentDAO;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("newDoc", Document.builder().date(LocalDate.now()).build());
        model.addAttribute("findDate", LocalDate.now());
        model.addAttribute("types", Type.values());
        return "home";
    }

    @PostMapping("/saveDoc")
    public String saveCar(Document document, Model model) {
        documentDAO.save(document);
        model.addAttribute("newDoc", document.toString());
        return "saved";
    }

    //    вивести всі документи в шаблон
    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<Document> documents = documentDAO.findAll();
        model.addAttribute("list", documents);
        return "documents";
    }

    @GetMapping("/doc-{id}")
    public String getOneDoc(@PathVariable int id, Model model){
//        Document one = documentDAO.findOne(id);
        Optional<Document> one = documentDAO.findById(id);
        model.addAttribute("doc", one);
        return "documentPage";
    }

    //    сортування за назвою
    @GetMapping("/sortName")
    public String sortName(Model model) {
        List<Document> all = documentDAO.findAll();
        Stream<Document> documentStream = all.stream().sorted((doc1, doc2) -> doc1.getName().compareTo(doc2.getName()));
        List<Document> documents = documentStream.collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    сортування за типом
    @GetMapping("/sortType")
    public String sortType(Model model) {
        List<Document> all = documentDAO.findAll();
        Stream<Document> documentStream = all.stream().sorted((doc1, doc2) -> doc1.getType().toString().compareTo(doc2.getType().toString()));
        List<Document> documents = documentStream.collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    сортування за розміром
    @GetMapping("/sortSize")
    public String sortSize(Model model) {
        List<Document> all = documentDAO.findAll();
        Stream<Document> documentStream = all.stream().sorted((doc1, doc2) -> (int) (doc1.getSize() - doc2.getSize()));
        List<Document> documents = documentStream.collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    пошук за назвою
    @GetMapping("/findName")
    public String findName(String name, Model model) {
        List<Document> all = documentDAO.findAll();
        List<Document> documents = all.stream().filter(document -> document.getName().equals(name)).collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    пошук за датою
    @GetMapping("/findDate")
    public String findDate(Document document, Model model) {
        List<Document> all = documentDAO.findAll();
        org.joda.time.LocalDate findDate = document.getDate();
        List<Document> documents = all.stream().filter(document1 -> findDate.equals(document1.getDate())).collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    пошук за діапазоном дат
    @GetMapping("/findDateInRange")
    public String findDateInRange(@DateTimeFormat(pattern = "yyyy-MM-dd")Date dateStart, @DateTimeFormat(pattern = "yyyy-MM-dd")Date dateFinish, Model model) {
        List<Document> all = documentDAO.findAll();
        LocalDate date1 = new LocalDate(dateStart);
        LocalDate date2 = new LocalDate(dateFinish);

        List<Document> documents = all.stream().filter(document1 -> document1.getDate().isAfter(date1) && document1.getDate().isBefore(date2) || document1.getDate().equals(date1) || document1.getDate().equals(date2)).collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    пошук за типом документу
    @GetMapping("/findType")
    public String findType(Type type, Model model) {
        List<Document> all = documentDAO.findAll();
        List<Document> documents = all.stream().filter(document -> document.getType() == type).collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

    //    пошук за розміром
    @GetMapping("/findSize")
    public String findSize(long size, Model model) {
        List<Document> all = documentDAO.findAll();
        List<Document> documents = all.stream().filter(document -> document.getSize() == size).collect(Collectors.toList());
        model.addAttribute("documents", documents);
        return "find";
    }

}
