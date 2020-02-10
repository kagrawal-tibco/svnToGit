package com.tibco.rta.service.cluster.impl;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.service.cluster.GMPLeaderElectionStrategy;
import com.tibco.rta.service.cluster.GroupMember;
import com.tibco.rta.service.cluster.GroupMembershipService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/7/13
 * Time: 9:43 AM
 * The member with greatest token value is returned.
 */
public class GreatestTokenLeaderElectionStrategy implements GMPLeaderElectionStrategy {

    @Override
    public <G extends GroupMember> G electLeader() {
        try {
            GroupMembershipService groupMembershipService = ServiceProviderManager.getInstance().getGroupMembershipService();

            List<G> allMembers = groupMembershipService.getAllMembers();

            return electLeader(allMembers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <G extends GroupMember> G electLeader(List<G> allMembers) {

        Collections.sort(allMembers, new Comparator<G>() {
            @Override
            public int compare(G g1, G g2) {
                String uid1 = g1.getUID();
                String uid2 = g2.getUID();

                int hashCode1 = uid1.hashCode();
                int hashCode2 = uid2.hashCode();

                if (hashCode1 > hashCode2) {
                    return 1;
                } else if (hashCode1 == hashCode2) {
                    return 0;
                } else if (hashCode1 < hashCode2) {
                    return -1;
                }
                return Integer.MIN_VALUE;
            }
        });
        return allMembers.get(allMembers.size() - 1);
    }
}
