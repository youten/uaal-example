#if UNITY_EDITOR
using UnityEditor;
#endif
using UnityEngine;

namespace SphereLibrary
{
    public class MessageReceiver : MonoBehaviour
    {
        // Messageの送信先：Sphere Spawner
        [SerializeField] private SphereSpawner sphereSpawner;

        /// <summary>
        /// NativeからUnitySendMessage
        /// </summary>
        /// <param name="message"></param>
        private void OnMessage(string message)
        {
            Debug.Log("message=" + message);
            sphereSpawner?.Spawn(message);
        }

#if UNITY_EDITOR
        /// <summary>
        /// エディタ拡張としてNativeからくるMessageを擬似発生させるUIを生成する
        /// </summary>
        [CustomEditor(typeof(MessageReceiver))]
        public class MessageReceiverEditor : Editor
        {
            public override void OnInspectorGUI()
            {
                base.OnInspectorGUI();
                var receiver = target as MessageReceiver;
                if (GUILayout.Button("SendMessage(\"white\")"))
                {
                    receiver?.OnMessage("white");
                }

                if (GUILayout.Button("SendMessage(\"red\")"))
                {
                    receiver?.OnMessage("red");
                }

                if (GUILayout.Button("SendMessage(\"blue\")"))
                {
                    receiver?.OnMessage("blue");
                }
            }
        }
#endif
    }
}