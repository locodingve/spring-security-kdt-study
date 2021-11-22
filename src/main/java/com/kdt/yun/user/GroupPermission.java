package com.kdt.yun.user;

import lombok.Getter;

import javax.persistence.*;

/**
 * Created by yunyun on 2021/11/22.
 */

@Entity
@Getter
@Table(name = "group_permission")
public class GroupPermission {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
