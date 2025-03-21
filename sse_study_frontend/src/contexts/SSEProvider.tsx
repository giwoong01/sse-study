import React, {
  createContext,
  useContext,
  useEffect,
  useCallback,
  useRef,
  ReactNode,
} from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import { useToast } from "../hooks/useToast.ts";

interface SSEContextType {}

const SSEContext = createContext<SSEContextType | undefined>(undefined);

interface SSEProviderProps {
  url: string;
  children: ReactNode;
}

export const SSEProvider: React.FC<SSEProviderProps> = ({ children }) => {
  const eventSourceRef = useRef<EventSourcePolyfill | null>(null);
  const { showToast } = useToast();

  const closeEventSource = () => {
    eventSourceRef.current?.close();
    eventSourceRef.current = null;
  };

  const connectToSSE = useCallback(() => {
    if (eventSourceRef.current?.readyState === 1) {
      console.log("이미 SSE에 연결되어 있습니다.");
      return;
    }

    eventSourceRef.current = new EventSourcePolyfill(
      `${process.env.REACT_APP_API_BASE_URL}/connect`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          Accept: "text/event-stream",
        },
        heartbeatTimeout: 86400000,
        withCredentials: true,
      }
    );

    eventSourceRef.current.onmessage = (event) => {
      // 받아오는 데이터로 할 일
      const hasNoSuccessMessage = !event.data.includes("이벤트 스트림 생성");

      if (hasNoSuccessMessage) {
        showToast(`${JSON.stringify(event.data)}`);
      }
    };

    eventSourceRef.current.onopen = () => {
      // 연결 시 할 일
      console.log("SSE 스트림 연결 성공");
    };

    eventSourceRef.current.onerror = (e: any) => {
      // 종료 또는 에러 발생 시 할 일
      console.error("SSE 에러 발생", e);
      closeEventSource();
      setTimeout(() => {
        connectToSSE();
      }, 3000);
    };
  }, [showToast]);

  useEffect(() => {
    connectToSSE();

    return () => {
      closeEventSource();
    };
  }, [connectToSSE]);

  return <SSEContext.Provider value={{}}>{children}</SSEContext.Provider>;
};

export const useSSE = () => {
  const context = useContext(SSEContext);
  if (context === undefined) {
    throw new Error("useSSE must be used within an SSEProvider");
  }
  return context;
};
