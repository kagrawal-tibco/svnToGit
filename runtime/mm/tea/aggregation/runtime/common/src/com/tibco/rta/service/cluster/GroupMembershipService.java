package com.tibco.rta.service.cluster;

import com.tibco.rta.common.GMPActivationListener;
import com.tibco.rta.common.service.StartStopService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/4/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public interface GroupMembershipService extends StartStopService {


    /**
     * The membership subject to which one or more SPM FT enabled engines will subscribe to.
     *
     * @return
     */
    String getMembershipSubject();


    /**
     * Subscribe to configured subject indicating interest in joining a group.
     */
    void subscribe() throws Exception;


    /**
     * Search for this member id in its own cached list of members.
     *
     * @param <G>
     * @return
     */
    <G extends GroupMember> G getOrAddMember(String memberUID);

    /**
     * Elect primary member when one or more join or when existing primary leaves.
     * <p>
     * Primary should be elected when quorum is met or quorum expiry is met
     * whichever is earlier.
     * </p>
     *
     * @param <G>
     * @return
     */
    <G extends GroupMember> G electPrimary();

    /**
     * Signal waiters that quorum is completed
     */
    <G extends GroupMember> void signalQuorumComplete(G... groupMembers);


    /**
     * Get all members joining this group. It may be more than/equal to/less than configured quorum.
     *
     * @param <G>
     * @return
     */
    <G extends GroupMember> List<G> getAllMembers();

    /**
     * Notification for interested parties about GMP events.
     *
     * @param listener
     * @param <L>
     */
    <L extends GroupMembershipListener> void addMembershipListener(L listener);

    /**
     *
     * @param messageReceivedListener
     * @param <M>
     */
    <M extends GMPActivationListener> void addActivationListener(M messageReceivedListener);

    /**
     * Signal primary to do something.
     */
    void signalPrimary();

    /**
     * Signal network disconnect to do something.
     */
    void signalNetworkDisconnect();

    /**
     * Publish notification for quorum completion.
     *
     * @throws Exception
     */
    void publishQuorumNotificationMessage() throws Exception;

    /**
     * Get cluster associated.
     * @param <C>
     * @return
     */
    <C extends GroupMembershipCluster> C getCluster();

    /**
     * Start election process.
     * @return
     */
    void startElection();

    /**
     * Start heartbeat sender.
     */
    void startMemberHeartbeats();

    /**
     * Start heartbeat scanner.
     */
    void startMemberLivenessScanner();

    /**
     * Stop heartbeat sender.
     */
    void stopMemberHeartbeats();

    /**
     * Stop heartbeat scanner.
     */
    void stopMemberLivenessScanner();
}
