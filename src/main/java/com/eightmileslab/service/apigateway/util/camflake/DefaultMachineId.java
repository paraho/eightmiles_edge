package com.eightmileslab.service.apigateway.util.camflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Generates Machine ID from latter 16-bits of the host's IP address.
 */
class DefaultMachineId implements MachineId {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(MachineId.class);

    @Override
    public int getId() {
        try {
            byte[] a = InetAddress.getLocalHost().getAddress();
            log.debug("Localhost IP address: {}", InetAddress.getLocalHost().getHostAddress());

            int machineId = (Byte.toUnsignedInt(a[2]))<<8 | Byte.toUnsignedInt(a[3]);
            log.debug("Calculated machineID: {}", machineId);
            return machineId;

        } catch (UnknownHostException e) {
            String message = "Failed to process machine id.";
            log.error(message, e);
            throw new CamflakeException(message, e);
        }
    }
}
