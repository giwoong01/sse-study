import { useEffect, useCallback, useRef } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";

// SSE연결 커스텀 훅 -> 사용 X

// static readonly CLOSED: 2;
// static readonly CONNECTING: 0;
// static readonly OPEN: 1;

export const useSSE = () => {
  const eventSourceRef = useRef<EventSourcePolyfill | null>(null);

  const connectToSSE = useCallback(() => {
    if (eventSourceRef.current?.readyState === 1) {
      return;
    }

    eventSourceRef.current = new EventSourcePolyfill(
      `${process.env.REACT_APP_API_BASE_URL}/connect`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          Accept: "text/event-stream",
        },
        heartbeatTimeout: 3600000,
        withCredentials: true,
      }
    );

    eventSourceRef.current.onmessage = (event) => {
      // 받아오는 데이터로 할 일
      console.log(event);
    };

    eventSourceRef.current.onopen = () => {
      // 연결 시 할 일
      console.log("SSE 스트림 연결 성공");
    };

    eventSourceRef.current.onerror = (e: any) => {
      // 종료 또는 에러 발생 시 할 일
      console.error("SSE 에러 발생", e);

      if (eventSourceRef.current) {
        console.log("SSE 연결 종료됨");
      }
    };
  }, []);

  useEffect(() => {
    connectToSSE();

    return () => {
      eventSourceRef.current?.close();
    };
  }, [connectToSSE]);
};
