import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import Button from "../components/Button.tsx";
import { Notifications } from "../types/Notification.ts";
import { NotificationsApi } from "../api/NotificaitonApi.ts";

const Notification: React.FC = () => {
  const navigate = useNavigate();
  const [notifications, setNotifications] = useState<Notifications>();

  const handleHomeClick = () => {
    navigate("/home");
  };

  useEffect(() => {
    const fetchNotification = async () => {
      try {
        const response = await NotificationsApi();

        if (response) {
          console.log(response);
          setNotifications(response);
        }
      } catch (error) {
        console.error("알림 조회 실패:", error);
      }
    };

    fetchNotification();
  }, []);

  return (
    <Container>
      <Title>Notification</Title>
      <List>
        {Array.isArray(notifications) &&
          notifications?.map((item, index) => (
            <ListItem key={index}>{item.message}</ListItem>
          ))}
      </List>
      <Button onClick={handleHomeClick}>홈으로 이동</Button>
    </Container>
  );
};

export default Notification;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f0f0f0;
`;

const Title = styled.h2`
  margin-bottom: 1.25rem;
  font-size: 1.5rem;
  color: #333;
`;

const List = styled.ul`
  list-style-type: none;
  padding: 0;
  margin: 1.25rem 0; /* 20px 0 */
  width: 100%;
  max-width: 600px;
  height: 30rem;
  overflow-y: auto;

  border-radius: 0.25rem;
`;

const ListItem = styled.li`
  background-color: #fff;
  padding: 0.625rem;
  margin-bottom: 0.625rem;
  border-radius: 0.25rem;
  box-shadow: 0 0 0.625rem rgba(0, 0, 0, 0.1);
`;
