package com.eagle6.service.web.rest;

import com.eagle6.service.domain.Project;
import com.eagle6.service.kafka.channel.ProducerChannel;
import com.eagle6.service.kafka.model.Greeting;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for working with projects
 */
@RestController
@RequestMapping("api/projects")
public class ProjectResource {

    private final Logger logger = LoggerFactory.getLogger(ProjectResource.class);

    private MessageChannel channel;

    public ProjectResource(ProducerChannel channel) {
        this.channel = channel.messageChannel();
    }

    @GetMapping(value = "", produces = "application/json")
    @ApiOperation("Return list of projects")
    @ApiResponses( value = {
        @ApiResponse(code = 200, message = "Successfully retrieved projects"),
        @ApiResponse(code = 404, message = "The resource you are trying to reach is not found")
    })
    @Timed
    public ResponseEntity<List<Project>> getProjects(){
        channel.send(MessageBuilder.withPayload(new Greeting().setMessage("Hello world!: ")).build());
        logger.debug("REST request to ger list of projects");
        List<Project> projects = new ArrayList<>();
        Project project = new Project();
        project.setName("test");
        projects.add(project);
        return  new ResponseEntity<>(projects,HttpStatus.OK);
    }
}
