import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { MemberInfoApi, MemberListApi } from "../api/MemberApi.ts";
import { MemberInfo, MembersInfo } from "../types/MemberInfo.ts";
import styled from "styled-components";
import Button from "../components/Button.tsx";
import { useSendMessage } from "../hooks/useSendMessage.ts";

const Home: React.FC = () => {
  const navigate = useNavigate();
  const [memberInfo, setMemberInfo] = useState<MemberInfo>();
  const [allMembers, setAllMembers] = useState<MembersInfo>();
  const { handleSend } = useSendMessage();

  const handleLoginPageClick = () => {
    navigate("/");
  };

  const handleNotificationClick = () => {
    navigate("/notification");
  };

  const handleSendMessage = (targetMemberId: number) => {
    handleSend(targetMemberId);
  };

  useEffect(() => {
    const fetchMemberInfo = async () => {
      try {
        const response = await MemberInfoApi();

        if (response) {
          setMemberInfo(response);
        }
      } catch (error) {
        console.error("회원정보 조회 실패:", error);
      }
    };

    const fetchAllMembers = async () => {
      try {
        const response = await MemberListApi();

        if (response) {
          setAllMembers(response);
        }
      } catch (error) {
        console.error("전체 회원 정보 조회 실패:", error);
      }
    };

    fetchMemberInfo();
    fetchAllMembers();
  }, []);

  return (
    <Container>
      <LeftSection>
        <Title>Home</Title>
        <InfoText>{memberInfo?.memberId}</InfoText>
        <InfoText>{memberInfo?.name}님 환영합니다. 현재 접속중입니다.</InfoText>
        <Button onClick={handleNotificationClick}>알림으로 이동</Button>
        <Spacer />
        <Button variant="danger" onClick={handleLoginPageClick}>
          로그아웃
        </Button>
      </LeftSection>
      <RightSection>
        <Title>전체 사용자</Title>
        <MemberList>
          {Array.isArray(allMembers) &&
            allMembers?.map((member) => (
              <MemberListItem
                key={member.memberId}
                $isSelf={member.memberId === memberInfo?.memberId}
              >
                (ID: {member.memberId}) {member.name}
                {member.memberId === memberInfo?.memberId && " (본인)"}
                <SendButton onClick={() => handleSendMessage(member.memberId)}>
                  Send
                </SendButton>
              </MemberListItem>
            ))}
        </MemberList>
      </RightSection>
    </Container>
  );
};

export default Home;

const Container = styled.div`
  display: flex;
  height: 100vh;
  background-color: #f0f0f0;
`;

const LeftSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  flex: 1;
  padding: 1rem;
`;

const RightSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  flex: 1;
  padding: 1rem;
  border-left: 1px solid #ccc;
  overflow-y: auto;
`;

const Title = styled.h2`
  margin-bottom: 1.25rem;
  font-size: 1.5rem;
  color: #333;
`;

const InfoText = styled.p`
  margin: 0.625rem 0;
  font-size: 1rem;
  color: #333;
`;

const Spacer = styled.div`
  height: 2rem;
`;

const MemberList = styled.ul`
  list-style-type: none;
  padding: 0;
  margin: 0;
  width: 100%;
`;

const MemberListItem = styled.li<{ $isSelf: boolean }>`
  background-color: ${({ $isSelf }) => ($isSelf ? "#d1e7dd" : "#fff")};
  padding: 0.625rem;
  margin-bottom: 0.625rem;
  border-radius: 0.25rem;
  box-shadow: 0 0 0.625rem rgba(0, 0, 0, 0.1);
  width: 100%;
  text-align: left;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const SendButton = styled.button`
  padding: 0.5rem;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 0.25rem;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;
