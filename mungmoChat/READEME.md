
### websocket
ws://localhost:8080/stomp/chat
/sub/meetup
/pub/meetup

{
"chatRoomId" : "1",
"senderId" : "1",
"senderNickName" : "hwang",
"content" : "test001"
}

### kafka
topic 생성
chat_meetup_message
chat_meetup_notification

### jmeter
open /opt/homebrew/bin/jmeter
처음 카프카를 통해 채팅서비스를 만들었을 땐, 많은 자료가 더 많은 tps 감당할 수 있을거라했다.
첫번째는 그 말을 믿고 진행했고 두번째는 어차피 컨슘에 들어와 로직처리하는건 스프링인데 과연 맞을까 생각했다.
결론은 200명 유저가 1초에 20번 메세지 발행 조건으로 미세한 차이로 더 낮은 tps를 보였다.
카프카 이용 시, 180/s 미사용 시, 187/s
카프카를 사용한다는 오버헤드로 인해 처리 속도가 조금 더 낮다는 결론이 났다.
다른 사람들은 왜 그런 글을 올렸을까.. 도저히 이해가 되지않아 알아보니, 이력서용으로 자작극을 한 것이였다.
그렇다면 채팅서비스를 구현하는데 카프카가 필요한 이유는 비동기 분산처리로 유용한 것 보다 디커플링인 것으로 현재는 결론을 지었다.
