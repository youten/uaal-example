using UnityEngine;

namespace SphereLibrary
{
    /// <summary>
    /// SphereをSpawnする
    /// </summary>
    public class SphereSpawner : MonoBehaviour
    {
        /// <summary>
        /// Spawn対象のWhite Sphere Prefab
        /// </summary>
        [SerializeField] private GameObject whiteSpherePrefab;

        /// <summary>
        /// Spawn対象のRed Sphere Prefab
        /// </summary>
        [SerializeField] private GameObject redSpherePrefab;

        /// <summary>
        /// Spawn対象のBlue Sphere Prefab
        /// </summary>
        [SerializeField] private GameObject blueSpherePrefab;

        /// <summary>
        /// Spawn interval
        /// </summary>
        [SerializeField] private float interval = 1.0f;

        private float _time;

        private void Update()
        {
            // interval間隔でSpawn
            _time += Time.deltaTime;
            if (_time > interval)
            {
                Spawn(whiteSpherePrefab);
                _time = 0;
            }
        }

        /// <summary>
        /// 指定した色のSphereをSpawnする
        /// </summary>
        /// <param name="color"></param>
        public void Spawn(string color)
        {
            if (string.IsNullOrEmpty(color) || color == "white")
            {
                Spawn(whiteSpherePrefab);
            }
            else if (color == "red")
            {
                Spawn(redSpherePrefab);
            }
            else if (color == "blue")
            {
                Spawn(blueSpherePrefab);
            }
        }

        private void Spawn(GameObject spherePrefab)
        {
            if (spherePrefab == null)
            {
                return; // do nothing
            }

            // SphereをSpawn
            var sphere = Instantiate(spherePrefab, transform.position, Quaternion.identity);

            // ランダムな回転初速を設定してやることでいい感じに転がるようにする
            var rb = sphere.GetComponent<Rigidbody>();
            if (rb != null)
            {
                rb.angularVelocity = Random.insideUnitSphere * 3.0f;
            }
        }
    }
}