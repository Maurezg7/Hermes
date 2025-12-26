package maudev.backend.service.impl;

import maudev.backend.model.entity.Channel;

import java.util.List;

public interface IChannelService {
    List<Channel> getAllChannels();
    Channel getChannelById(Long id);
    Channel saveChannel(Channel channel);
    void deleteChannel(Long id);
}
