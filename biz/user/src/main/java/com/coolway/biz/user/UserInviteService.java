package com.coolway.biz.user;

import static org.apache.commons.lang.StringUtils.isEmpty;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolway.biz.user.meta.InviteStatus;
import com.coolway.dao.InviteMapper;
import com.coolway.entity.Invite;
import com.coolway.entity.InviteExample;

@Service
public class UserInviteService {

	@Autowired
	private InviteMapper inviteMapper;

	public void inviteUser(Integer userId, String invitee, String code) {
		if (userId == null || isEmpty(invitee) || isEmpty(code)) {
			throw new IllegalArgumentException("userId, invitee or code null");
		}
		Invite invite = new Invite();
		invite.setUserId(userId);
		invite.setInvitee(invitee);
		invite.setCode(code);
		invite.setCreateTime(new Date());
		invite.setStatus(InviteStatus.NEW.getStatus());
		inviteMapper.insertSelective(invite);
	}

	public int handleInvite(String email, String code) {
		if (isEmpty(email) || isEmpty(code)) {
			throw new IllegalArgumentException("email or code null");
		}

		InviteExample example = new InviteExample();
		example.createCriteria().andCodeEqualTo(code).andInviteeEqualTo(email)
				.andStatusEqualTo(InviteStatus.SENDED.getStatus());
		Invite invite = new Invite();
		invite.setInvitee(email);
		invite.setCode(code);
		invite.setStatus(InviteStatus.ACCEPTED.getStatus());
		invite.setModifyTime(new Date());
		return inviteMapper.updateByExampleSelective(invite, example);
	}

}
