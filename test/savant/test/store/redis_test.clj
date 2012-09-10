(ns savant.test.store.redis-test
  (:require [clojure.test :refer :all]
            [taoensso.carmine :as redis]
            [savant.test.store-spec :refer :all]
            [savant.store.redis :refer (carmine get-event-store)]
            [savant.store.redis]))

(deftest redis-event-store-tests
  (let [bucket-name "event-store-tests"
        dummy-store (get-event-store {})
        old-test-keys (carmine dummy-store
                               (redis/keys (format "%s*" bucket-name)))]

    (carmine dummy-store
             (doseq [k old-test-keys] (redis/del k)))

    (testing "testing redis event store:"
      (let [ns 'savant.store.redis
            valid-opts {:test-bucket bucket-name}
            invalid-opts {}]
        (get-event-store-constructor-tests ns valid-opts invalid-opts)
        (event-store-protocol-tests ns valid-opts)
        (event-stream-protocol-tests ns valid-opts)))))
