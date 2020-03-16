/**
 *  Etekcity Remote Control
 *
 *  Copyright 2018 Abhineet Garg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "Etekcity Remote Control", namespace: "NeetArt", author: "Abhineet Garg") {
		capability "Actuator"
		capability "Sensor"
		capability "Switch"
	}

	tiles {
		standardTile("button", "device.switch", width: 3, height: 3, canChangeIcon: true) {
			state "off", label: 'Off', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
			state "on", label: 'On', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821", nextState: "off"
		}
	}
    
    preferences {
    	input name: "ipAddress", type: "text", title: "Ip Address", description: "Enter Device IP Address", required: true
    	input name: "port", type: "number", title: "Port Number", description: "Enter Device Port Number", required: true
        input name: "switchNumber", type: "number", title: "Switch number", description: "Enter switch number as mentioned on remote", required: true
    }
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

// handle commands
def on() {
	log.debug "Executing 'on'"
	executePostCall("on")
    sendEvent(name: "switch", value: "on")
}

def off() {
	log.debug "Executing 'off'"
    executePostCall("off")
    sendEvent(name: "switch", value: "off")
}

private executePostCall(String operationName) {
	try {
        def urlPath = "/switches/${operationName}/${apiSwitchNumber}"

        log.debug "Executing POST call HOST:${hostAddress} PATH:${urlPath}"
        def hubAction = new physicalgraph.device.HubAction(
            method: "POST",
            path: urlPath,
            headers: [HOST:hostAddress]
        )

        sendHubCommand(hubAction)
        log.debug "Successfully executed call. Response: ${hubAction}"
    } catch (e) {
    	log.warning "Failed to execute call. Error: ${e}"
    }
}

private getHostAddress() {
	return ipAddress + ":" + port
}

private getApiSwitchNumber() {
	return switchNumber - 1
}