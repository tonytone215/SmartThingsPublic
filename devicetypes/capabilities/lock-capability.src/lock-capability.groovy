/**
 *  Copyright 2015 SmartThings
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
	definition (name: "Lock Capability", namespace: "capabilities", author: "SmartThings") {
		capability "Lock"
	}

	simulator {
		status "locked": "lock:locked"
		status "unlocked": "lock:unlocked"

		reply "lock": "lock:locked"
		reply "unlock": "lock:unlocked"
	}

	tiles {
		standardTile("toggle", "device.lock", width: 2, height: 2) {
			state "unlocked", label:'unlocked', action:"lock.lock", icon:"st.locks.lock.unlocked", backgroundColor:"#ffffff"
			state "locked", label:'locked', action:"lock.unlock", icon:"st.locks.lock.locked", backgroundColor:"#00A0DC"
		}
		standardTile("lock", "device.lock", inactiveLabel: false, decoration: "flat") {
			state "default", label:'lock', action:"lock.lock", icon:"st.locks.lock.locked"
		}
		standardTile("unlock", "device.lock", inactiveLabel: false, decoration: "flat") {
			state "default", label:'unlock', action:"lock.unlock", icon:"st.locks.lock.unlocked"
		}

		main "toggle"
		details(["toggle", "lock", "unlock", "refresh"])
	}
}

def parse(String description) {
	def pair = description.split(":")
	createEvent(name: pair[0].trim(), value: pair[1].trim())
}

def lock() {
	"lock"
}

def unlock() {
	"unlock"
}