import {CHAT_SOCKET_MUTATION, CURRENT_CHAT_MUTATION} from './mutations-type'

export default {
  [CHAT_SOCKET_MUTATION] (state, socket) {
    state.chatSocket = socket
  },
  [CURRENT_CHAT_MUTATION] (state, currentChat) {
    state.currentChat = currentChat
  }
}
