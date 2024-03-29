package gr.hua.dit.ds.reference.letter.service.controller;

import gr.hua.dit.ds.reference.letter.service.entity.*;
import gr.hua.dit.ds.reference.letter.service.payload.ReferenceLetterRequestDto;
import gr.hua.dit.ds.reference.letter.service.payload.TeacherDto;
import gr.hua.dit.ds.reference.letter.service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST API Student Controller
 *
 * The REST API Student Controller provides operations for users of type 'Student'
 *
 * @author Panagiotis Bellias
 * @since 2022-02-18
 * @version 1.0
 */
@RestController
@Secured("ROLE_STUDENT")
@RequestMapping("/api/app/reference_letter_requests")
public class ApiStudentController {

    @Autowired
    private ReferenceLetterRequestRepository referenceLetterRequestRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    /**
     * Create Reference Letter Request Method
     * With this method students are able to create a new reference letter request
     * @param referenceLetterRequest, a dto object to represent the data student wants for its new request.
     * @param authentication, is an object to take information for the current session
     *                        using the Authentication autowired bean
     * @return the instance of this new reference letter request
     */
    @PostMapping("/")
    public ResponseEntity<?> createRLrequest(@Validated
            @RequestBody ReferenceLetterRequestDto referenceLetterRequest, Authentication authentication) {
        ReferenceLetterRequest rl = new ReferenceLetterRequest();
        String username = authentication.getName();
        Student student = studentRepository.findStudentByUser(username);
        rl.setStudent(student);
        Teacher teacher = teacherRepository.getById(referenceLetterRequest.getTeacher().getId());
        rl.setTeacher(teacher);
        rl.setCarrierName(referenceLetterRequest.getCarrierName());
        rl.setCarrierEmail(referenceLetterRequest.getCarrierEmail());
        rl.setPending(true);
        referenceLetterRequestRepository.save(rl);
        return new ResponseEntity<>("Reference Letter Request is created!", HttpStatus.OK); // inform user
    }

    /**
     * Get Reference Letter Requests Method
     * With this method students are able to view all the reference letter requests
     * @param authentication, is an object to take information for the current session
     *                        using the Authentication autowired bean
     * @return all reference letter requests that exist in database as a list
     */
    @GetMapping("/")
    public ResponseEntity<ArrayList<ReferenceLetterRequestDto>> getRLrequests(Authentication authentication) {

        String username = authentication.getName();
        Student student = studentRepository.findStudentByUser(username);
        System.out.println("!!! Student = " + student);
        ArrayList<ReferenceLetterRequest> list =
                (ArrayList<ReferenceLetterRequest>) referenceLetterRequestRepository.findReferenceLetterRequestsByStudent(student.getId());
        ArrayList<ReferenceLetterRequestDto> result = new ArrayList<>();

        for (ReferenceLetterRequest rl : list) {
            ReferenceLetterRequestDto rl_dto = new ReferenceLetterRequestDto();
            TeacherDto teacher = new TeacherDto();
            teacher.setId(rl.getTeacher().getId());
            teacher.setFullName(rl.getTeacher().getFullName());
            teacher.setEmail(rl.getTeacher().getEmail());
            teacher.setDescription(rl.getTeacher().getDescription());
            rl_dto.setId(rl.getId());
            rl_dto.setTeacher(teacher);
            rl_dto.setCarrierName(rl.getCarrierName());
            rl_dto.setCarrierEmail(rl.getCarrierEmail());
            if (rl.getText() != null) rl_dto.setText(rl.getText());
            if (rl.isApproved()) rl_dto.setStatus("approved");
            else if (rl.isDeclined()) rl_dto.setStatus("declined");
            result.add(rl_dto);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * Get More Info Method
     * With this method students are able to view details about a reference letter request
     * @param id, is the id of a certain reference letter request
     * @return a reference letter request object
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReferenceLetterRequestDto> getMoreInfo(@PathVariable(value = "id") Integer id) {
        Optional<ReferenceLetterRequest> referenceLetterRequest = referenceLetterRequestRepository.findById(id);

        if (referenceLetterRequest.isPresent()) {
            ReferenceLetterRequestDto referenceLetterRequestDto = new ReferenceLetterRequestDto();
            TeacherDto teacherDto = new TeacherDto();
            Teacher teacher = referenceLetterRequest.get().getTeacher();
            teacherDto.setFullName(teacher.getFullName());
            teacherDto.setEmail(teacher.getEmail());
            teacherDto.setDescription(teacher.getDescription());

            referenceLetterRequestDto.setId(referenceLetterRequest.get().getId());
            referenceLetterRequestDto.setTeacher(teacherDto);
            referenceLetterRequestDto.setCarrierName(referenceLetterRequest.get().getCarrierName());
            referenceLetterRequestDto.setCarrierEmail(referenceLetterRequest.get().getCarrierEmail());
            if (referenceLetterRequest.get().getText() != null)
                referenceLetterRequestDto.setText(referenceLetterRequest.get().getText());
            if (referenceLetterRequest.get().isApproved()) referenceLetterRequestDto.setStatus("approved");
            else if (referenceLetterRequest.get().isDeclined()) referenceLetterRequestDto.setStatus("declined");

            return ResponseEntity.ok().body(referenceLetterRequestDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRLrequest(@RequestBody ReferenceLetterRequestDto referenceLetterRequestDto,@PathVariable(value = "id") Integer id) {
        Optional<ReferenceLetterRequest> referenceLetterRequest = referenceLetterRequestRepository.findById(id);
        if(referenceLetterRequest.isEmpty()){
            return new ResponseEntity<>("Reference Letter Not Found",
                    HttpStatus.BAD_REQUEST);
        }

        ReferenceLetterRequest rl = referenceLetterRequest.get();

        TeacherDto teacherDto = referenceLetterRequestDto.getTeacher();
        Teacher teacher = teacherRepository.getById(teacherDto.getId());

        rl.setTeacher(teacher);
        rl.setCarrierName(referenceLetterRequestDto.getCarrierName());
        rl.setCarrierEmail(referenceLetterRequestDto.getCarrierEmail());
        referenceLetterRequestRepository.save(rl);
        return new ResponseEntity<>("Reference Letter Updates", HttpStatus.OK);
    }

    /**
     * Delete Reference Letter Request Method
     * With this method students are able to delete a reference letter request
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRLrequest(@PathVariable(value = "id") Integer id, Authentication authentication) {

        String username = authentication.getName();
        Student student = studentRepository.findStudentByUser(username);
        Optional<ReferenceLetterRequest> referenceLetterRequest =
                referenceLetterRequestRepository.findReferenceLetterRequestByStudent(id, student.getId());
        if (referenceLetterRequest.isEmpty())
            return new ResponseEntity<>("Reference Letter Request Not Found!",
                    HttpStatus.NOT_FOUND); // inform user

        referenceLetterRequestRepository.delete(referenceLetterRequest.get());
        return new ResponseEntity<>("Your reference letter request has been deleted!",
                HttpStatus.OK); // inform user

    }
}