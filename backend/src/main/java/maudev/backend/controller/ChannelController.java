package maudev.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import maudev.backend.model.entity.Channel;
import maudev.backend.service.ChannelService;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hermes-app")
@CrossOrigin(value = "http://localhost/4200")
public class ChannelController {
    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
    
    @Autowired
    private ChannelService channelService;
    
    @GetMapping("/channel")
    public List<Channel> getAllChannels(){
        List<Channel> channels = this.channelService.getAllChannels();
        if(channels.isEmpty()){
            throw new RuntimeException("Channel list is empty.");
        }
        channels.forEach(channel -> {
            logger.info(channel.toString());
        });
        return channels;
    }
    
    @PostMapping("/channel")
    public Channel createChannel(@RequestBody Channel channel){
        if(channel == null){
            throw new RuntimeException("Channel object is null");
        }
        logger.info("Channel created.");
        return this.channelService.saveChannel(channel);
    }
    
    @GetMapping("/channel/{id}")
    public ResponseEntity<Channel> getChannelById(@PathVariable("id") Long id){
        Channel channel = this.channelService.getChannelById(id);
        if(channel == null){
            throw new RuntimeException("Channel not found.");
        }
        return ResponseEntity.ok(channel);
    }
    
    @PutMapping("/channel/{id}")
    public ResponseEntity<Channel> updateChannel(@PathVariable("id") Long id, @RequestBody Channel channelReceived){
        Channel channel = this.channelService.getChannelById(id);
        if(channel == null){
            throw new RuntimeException("Channel not found.");
        }
        channel.setName(channelReceived.getName());
        channel.setDescription(channelReceived.getDescription());
        channel.setServer_id(channelReceived.getServer_id());
        this.channelService.saveChannel(channel);
        return ResponseEntity.ok(channel);
    }
    
    @DeleteMapping("/channel/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteChannel(@PathVariable("id") Long id){
        Channel channel = channelService.getChannelById(id);
        if(channel == null){
            throw new RuntimeException("Channel not found.");
        }
        this.channelService.deleteChannel(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
